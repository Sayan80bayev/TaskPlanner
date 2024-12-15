package com.example.tasks.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.data.TaskDatabase
import com.example.tasks.data.repository.UserRepository
import com.example.tasks.databinding.FragmentRegisterBinding
import com.example.tasks.ui.AuthViewModel
import com.example.tasks.ui.AuthViewModelFactory
import com.example.tasks.data.model.User

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(UserRepository(TaskDatabase.getInstance(requireContext()).userDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(email = email, password = password)
                authViewModel.register(
                    user,
                    onSuccess = {
                        Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                        // Переход на экран логина
                        parentFragmentManager.popBackStack()
                    },
                    onError = { error ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}