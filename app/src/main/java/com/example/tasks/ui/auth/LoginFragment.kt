package com.example.tasks.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasks.data.TaskDatabase
import com.example.tasks.data.repository.UserRepository
import com.example.tasks.databinding.FragmentLoginBinding
import com.example.tasks.ui.AuthViewModel
import com.example.tasks.ui.AuthViewModelFactory
import com.example.tasks.ui.context.UserSession

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(UserRepository(TaskDatabase.getInstance(requireContext()).userDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(
                    email,
                    password,
                    onSuccess = { user ->
                        UserSession.currentUser = user
                        Toast.makeText(requireContext(), "Welcome ${user.email}", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListFragment(null))
                    },
                    onError = {
                        Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        return binding.root
    }
}