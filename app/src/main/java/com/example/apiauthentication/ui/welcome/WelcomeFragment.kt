package com.example.apiauthentication.ui.welcome

import androidx.navigation.fragment.findNavController
import com.example.apiauthentication.databinding.FragmentWelcomeBinding
import com.example.apiauthentication.ui.base.BaseFragment


class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment())
        }
    }
}