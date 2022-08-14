package com.example.apiauthentication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiauthentication.model.login.LoginResponse
import com.example.apiauthentication.model.login.LoginSend
import com.example.apiauthentication.network.RetrofitClient
import com.example.apiauthentication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

class LogInViewModel : ViewModel() {

    private val _logInState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading(true))
    val logInState = _logInState.asStateFlow()

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            when {
                !email.contains('@') -> {
                    _logInState.emit(Resource.Failure("email must contain @"))
                }
                password.length < 7 -> {
                    _logInState.emit(Resource.Failure("password must contain 8 character"))
                }
                else -> {
                    loginResponse(email = email, password = password).collect {
                        _logInState.value = it
                    }
                }
            }
        }
    }

    private fun loginResponse(email: String, password: String) = flow {
        try {
            val response = RetrofitClient.authService.logIn(LoginSend(email, password))
            when {
                response.isSuccessful -> {
                    val body = response.body()!!
                    emit(Resource.Success(body))
                }
                else -> {
                    val error = response.errorBody()
                    emit(Resource.Failure(errorMessage = error?.string() ?: "error"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}