package com.example.apiauthentication.ui.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.apiauthentication.databinding.LogInFragmentBinding
import com.example.apiauthentication.ui.base.BaseFragment
import com.example.apiauthentication.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<LogInFragmentBinding>(LogInFragmentBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun start() {
        listeners()
        observer()
    }

    private fun listeners(){
        binding.btnLogIn.setOnClickListener {
            viewModel.logIn(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun observer(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.logInState.collect{
                    when(it){
                        is Resource.Success ->{
                            Toast.makeText(context, it.data.token, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading ->{
                            Toast.makeText(context, "loading...", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}