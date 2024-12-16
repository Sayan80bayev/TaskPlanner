    package com.example.tasks.data

    import androidx.room.Dao
    import androidx.room.Delete
    import androidx.room.Insert
    import androidx.room.Query
    import androidx.room.Update
    import com.example.tasks.data.model.Task
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface TaskDao {
        // Query to get all tasks sorted by time for a specific user
        @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY isDone, dateTime, priority DESC, id")
        fun allTasksSortTime(userId: Int): Flow<List<Task>>

        // Query to get all tasks sorted by priority for a specific user
        @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY isDone, priority DESC, dateTime, id")
        fun allTasksSortPriority(userId: Int): Flow<List<Task>>

        // Query to get tasks within a date range, sorted by time for a specific user
        @Query("SELECT * FROM tasks WHERE userId = :userId AND dateTime >= :startDate AND dateTime < :endDate ORDER BY isDone, dateTime, priority DESC, id")
        fun dateTasksSortTime(userId: Int, startDate: String, endDate: String): Flow<List<Task>>

        // Query to get tasks within a date range, sorted by priority for a specific user
        @Query("SELECT * FROM tasks WHERE userId = :userId AND dateTime >= :startDate AND dateTime < :endDate ORDER BY isDone, priority DESC, dateTime, id")
        fun dateTasksSortPriority(userId: Int, startDate: String, endDate: String): Flow<List<Task>>

        // Query to search tasks by title or category for a specific user, sorted by time
        @Query("SELECT * FROM tasks WHERE userId = :userId AND (title LIKE :query OR category LIKE :query) ORDER BY isDone, dateTime, priority DESC, id")
        fun searchTasksSortTime(userId: Int, query: String): Flow<List<Task>>

        // Query to search tasks by title or category for a specific user, sorted by priority
        @Query("SELECT * FROM tasks WHERE userId = :userId AND (title LIKE :query OR category LIKE :query) ORDER BY isDone, priority DESC, dateTime, id")
        fun searchTasksSortPriority(userId: Int, query: String): Flow<List<Task>>

        // Insert a task
        @Insert
        suspend fun insertTask(task: Task)

        // Update a task
        @Update
        suspend fun updateTask(task: Task)

        // Delete a task
        @Delete
        suspend fun deleteTask(task: Task)

        // Delete all tasks for a specific user
        @Query("DELETE FROM tasks WHERE userId = :userId")
        suspend fun deleteAllTasks(userId: Int)
    }