package com.mikaocto.mikacore.ui.splash.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mikaocto.core.base.BaseFragmentBinding
import com.mikaocto.mikacore.databinding.FragmentSplashBinding
import com.mikaocto.mikacore.ui.MainActivity
import com.mikaocto.mikacore.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragmentBinding<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkUser()
        viewModel.splashViewState.observe(viewLifecycleOwner) {
            when (it) {
                is SplashViewModel.SplashViewState.UserData -> {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                SplashViewModel.SplashViewState.OnEmptyUser -> findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                )
            }
        }
    }
}