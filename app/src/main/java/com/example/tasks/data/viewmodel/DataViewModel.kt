package com.example.tasks.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.TaskDatabase
import com.example.tasks.data.model.Sorting
import com.example.tasks.data.model.Task
import com.example.tasks.data.repository.TaskRepository
import com.example.tasks.ui.context.UserSession
import com.example.tasks.utils.DateTimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    private val repository = TaskRepository(taskDao)

    private val userId = UserSession.currentUser!!.id // Get the user ID from the session

    private val sortFlow =
        MutableStateFlow(Triple<OffsetDateTime?, Sorting, String?>
            (DateTimeUtil.todayStart, Sorting.defaultSort, null))

    @ExperimentalCoroutinesApi
    private val taskListFlow = sortFlow
        .flatMapLatest {
            val date = it.first
            val sorting = it.second
            val search = it.third

            when (sorting) {
                Sorting.BY_TIME -> {
                    if (search != null) {
                        repository.searchTasksSortTime(userId, "%$search%") // Pass userId to repository
                    } else if (date != null) {
                        val startDate = DateTimeUtil.dateToTimestamp(date)
                        val endDate = DateTimeUtil.dateToTimestamp(date.plusDays(1))
                        repository.dateTasksSortTime(userId, startDate, endDate) // Pass userId to repository
                    } else {
                        repository.allTasksSortTime(userId) // Pass userId to repository
                    }
                }

                Sorting.BY_PRIORITY -> {
                    if (search != null) {
                        repository.searchTasksSortPriority(userId, "%$search%") // Pass userId to repository
                    } else if (date != null) {
                        val startDate = DateTimeUtil.dateToTimestamp(date)
                        val endDate = DateTimeUtil.dateToTimestamp(date.plusDays(1))
                        repository.dateTasksSortPriority(userId, startDate, endDate) // Pass userId to repository
                    } else {
                        repository.allTasksSortPriority(userId) // Pass userId to repository
                    }
                }
            }
        }

    @ExperimentalCoroutinesApi
    val taskList = taskListFlow.asLiveData()

    fun updateTaskList(triple: Triple<OffsetDateTime, Sorting, String?>) {
        sortFlow.value = triple
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks(userId) // Pass userId to repository
        }
    }
}