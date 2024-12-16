package com.example.tasks.data.repository

import com.example.tasks.data.TaskDao
import com.example.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // Pass userId when calling the DAO methods
    fun allTasksSortTime(userId: Int): Flow<List<Task>> = taskDao.allTasksSortTime(userId)

    fun allTasksSortPriority(userId: Int): Flow<List<Task>> = taskDao.allTasksSortPriority(userId)

    fun dateTasksSortTime(userId: Int, startDate: String, endDate: String): Flow<List<Task>> =
        taskDao.dateTasksSortTime(userId, startDate, endDate)

    fun dateTasksSortPriority(userId: Int, startDate: String, endDate: String): Flow<List<Task>> =
        taskDao.dateTasksSortPriority(userId, startDate, endDate)

    fun searchTasksSortTime(userId: Int, query: String): Flow<List<Task>> =
        taskDao.searchTasksSortTime(userId, query)

    fun searchTasksSortPriority(userId: Int, query: String): Flow<List<Task>> =
        taskDao.searchTasksSortPriority(userId, query)

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks(userId: Int) {
        taskDao.deleteAllTasks(userId)
    }
}