package com.example.apiauthentication.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apiauthentication.R
import com.example.apiauthentication.databinding.RegisterFragmentBinding
import com.example.apiauthentication.ui.base.BaseFragment
import com.example.apiauthentication.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>(
    RegisterFragmentBinding::inflate,
    RegisterViewModel::class.java
) {


    override fun start() {
        listeners()
        observer()
    }

    private fun listeners() {
        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.etEmail.text.toString(),
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                context,
                                getString(R.string.registered_successfully) + it.data.token,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

}