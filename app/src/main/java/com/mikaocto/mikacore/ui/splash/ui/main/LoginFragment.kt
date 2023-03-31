package com.mikaocto.mikacore.ui.splash.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.mikaocto.core.base.BaseFragmentBinding
import com.mikaocto.mikacore.R
import com.mikaocto.mikacore.databinding.LoginFragmentBinding
import com.mikaocto.mikacore.model.User
import com.mikaocto.mikacore.ui.MainActivity
import com.mikaocto.mikacore.ui.splash.SplashViewModel

class LoginFragment : BaseFragmentBinding<LoginFragmentBinding>(LoginFragmentBinding::inflate) {
    private val viewModel: SplashViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnLogin.setOnClickListener {
            validateUserInput()
        }
        viewModel.loginViewState.observe(viewLifecycleOwner) {
            when (it) {
                is SplashViewModel.LoginViewState.OnErrorSaveLogin -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                SplashViewModel.LoginViewState.OnSuccessLogin -> {
                    Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun validateUserInput() {
        val password = viewBinding.etPassword.text.toString()
        val username = viewBinding.etUsername.text.toString()

        if (password.isEmpty())
            viewBinding.tilPassword.error = getString(R.string.login_empty_error)
        if (username.isEmpty())
            viewBinding.tilUsername.error = getString(R.string.login_empty_error)

        if (username.isNotEmpty() && password.isNotEmpty())
            viewModel.insertUser(
                user = User(
                    name = username
                )
            )
    }
}