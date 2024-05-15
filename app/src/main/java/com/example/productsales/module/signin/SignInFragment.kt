package com.example.quickstartlessons.module.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quickstartlessons.MainActivity
import com.example.quickstartlessons.QSApplication
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.FragmentSignInBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import com.example.quickstartlessons.module.base.utils.PreferencesManager
import com.example.quickstartlessons.module.base.utils.QsConstants
import com.example.quickstartlessons.module.base.utils.splashActivity


class SignInFragment : BaseFragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()

    }

    private fun setupListener() {
        binding.forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionGlobalResetPasswordFragment())
        }
        binding.signInButton.setOnClickListener {
            validate()
        }
    }


    private fun validate() {
        val userName = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        when {
            isValidEmail(userName) && isValidPassword(password) -> {
                val users = splashActivity?.viewModel?.usersLiveData?.value?.users

                val user = users?.find {
                    it.username == userName && it.password == password
                }
                user?.let {
                    if (binding.rememberMeCheckbox.isChecked) {
                        PreferencesManager.putUserPassword(it.password)
                        PreferencesManager.putUserName(it.username)
                        QSApplication.userProfileLiveData.value = it
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    } else

                        !binding.rememberMeCheckbox.isChecked
                    QSApplication.userProfileLiveData.value = it
                    startActivity(Intent(requireContext(), MainActivity::class.java))


                }
            }

            !isValidEmail(userName) && !isValidPassword(password) -> {
                binding.emailUsernameInputLayout.error = getString(R.string.invalid_email)
                binding.passwordInputLayout.error = getString(R.string.invalid_password)
            }

            isValidEmail(userName) && !isValidPassword(password) -> {
                binding.emailUsernameInputLayout.error = QsConstants.EMPTY_STRING
                binding.passwordInputLayout.error = getString(R.string.invalid_password)
            }

            !isValidEmail(userName) && isValidPassword(password) -> {
                binding.emailUsernameInputLayout.error = getString(R.string.invalid_email)
                binding.passwordInputLayout.error = QsConstants.EMPTY_STRING
            }

            userName.isEmpty() && password.isEmpty() -> {
                binding.emailUsernameInputLayout.error = getString(R.string.field_required)
                binding.passwordInputLayout.error = getString(R.string.field_required)
            }
        }
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6
    private fun isValidEmail(userName: String): Boolean = true


}



