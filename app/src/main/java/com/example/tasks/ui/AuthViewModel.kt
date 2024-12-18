package com.example.tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.model.User
import com.example.tasks.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {

                if(userRepository.getUserByEmail(user.email) != null){
                    onError("User already exists")
                    return@launch
                }

                userRepository.register(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    fun login(email: String, password: String, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userRepository.login(email, password)
                if (user != null) {
                    onSuccess(user)
                } else {
                    onError("Invalid email or password")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}