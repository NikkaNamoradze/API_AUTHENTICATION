package com.example.apiauthentication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

typealias inflate<T> = (LayoutInflater, ViewGroup, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflater: inflate<VB>,
    private val viewModelClass: Class<VM>
) : Fragment() {

    protected val viewModel: VM by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = this.inflater.invoke(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun start()

}