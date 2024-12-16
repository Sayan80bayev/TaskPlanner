package com.example.tasks.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.tasks.utils.DateTimeUtil
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Entity(
    tableName = "tasks",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["userId"])]
)
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var dateTime: OffsetDateTime = OffsetDateTime.now(),
    var category: Category = Category.NONE,
    var priority: Priority = Priority.NONE,
    var isDone: Boolean = false,

    val userId: Int // Foreign key column
) : Parcelable