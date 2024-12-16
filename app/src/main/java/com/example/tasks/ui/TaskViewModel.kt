package com.example.tasks.ui

import androidx.lifecycle.ViewModel
import com.example.tasks.data.model.Category
import com.example.tasks.data.model.Priority
import com.example.tasks.data.model.Task
import com.example.tasks.ui.context.UserSession
import com.example.tasks.utils.DateTimeUtil
import kotlinx.coroutines.flow.MutableStateFlow

open class TaskViewModel : ViewModel() {
    var id = 0
    val title = MutableStateFlow("")
    val description = MutableStateFlow("")
    val dateTime = MutableStateFlow(DateTimeUtil.todayEnd)
    val category = MutableStateFlow(Category.NONE)
    val priority = MutableStateFlow(Priority.NONE)
    val isDone = MutableStateFlow(false)
    val userId = MutableStateFlow(UserSession.currentUser!!.id) // New: Tracks current user's ID
    val activeDate = MutableStateFlow(false)

    // Build a Task object with the userId
    fun buildTask() = Task(
        id = id,
        title = title.value,
        description = description.value,
        dateTime = dateTime.value,
        category = category.value,
        priority = priority.value,
        isDone = isDone.value,
        userId = userId.value // Include the foreign key
    )

    // Populate ViewModel fields from an existing Task
    fun fromTask(task: Task) {
        id = task.id
        title.value = task.title
        description.value = task.description
        dateTime.value = task.dateTime
        category.value = task.category
        priority.value = task.priority
        isDone.value = task.isDone
        userId.value = task.userId // Set the userId for the task
    }

    // Update the isDone status
    fun setIsDone(checked: Boolean) {
        isDone.value = checked
    }
}