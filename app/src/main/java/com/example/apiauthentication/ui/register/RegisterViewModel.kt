package com.example.apiauthentication.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiauthentication.model.register.RegisterResponse
import com.example.apiauthentication.model.register.RegisterSend
import com.example.apiauthentication.network.RetrofitClient
import com.example.apiauthentication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel : ViewModel() {

    private val _registerState =
        MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading(true))
    val registerState = _registerState.asStateFlow()

    fun register(email: String, userName: String, password: String) {
        viewModelScope.launch {
            when {
                !email.contains('@') -> {
                    _registerState.emit(Resource.Failure("email must contain @"))
                }
                userName.length <= 8 -> {
                    _registerState.emit(Resource.Failure("username must contain 8 characters"))
                }
                password.length <= 7 -> {
                    _registerState.emit(Resource.Failure("password must contain 7 characters"))
                }
                else -> {
                    registerResponse(email, password).collect {
                        _registerState.value = it
                    }
                }
            }
        }
    }

    private fun registerResponse(email: String, password: String) = flow {

        val response = RetrofitClient.authService.register(RegisterSend(email, password))

        try {
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