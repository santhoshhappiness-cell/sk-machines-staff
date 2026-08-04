package com.santhosh.dailyactivitytracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val category: String = "General",
    val priority: Int = 1, // 1: Low, 2: Medium, 3: High
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    val completedAt: String? = null,
    val dueDate: String? = null
)

enum class TaskCategory {
    Work, Personal, Health, Shopping, Learning, Other
}

enum class TaskPriority(val level: Int) {
    Low(1), Medium(2), High(3)
}
