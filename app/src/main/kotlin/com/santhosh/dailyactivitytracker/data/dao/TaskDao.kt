package com.santhosh.dailyactivitytracker.data.dao

import androidx.room.*
import com.santhosh.dailyactivitytracker.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<Task?>

    @Query("SELECT * FROM tasks ORDER BY priority DESC, createdAt DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY priority DESC, createdAt DESC")
    fun getActiveTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE category = :category ORDER BY priority DESC, createdAt DESC")
    fun getTasksByCategory(category: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 AND priority = :priority ORDER BY createdAt DESC")
    fun getTasksByPriority(priority: Int): Flow<List<Task>>

    @Query("DELETE FROM tasks WHERE isCompleted = 1")
    suspend fun clearCompletedTasks()

    @Query("SELECT COUNT(*) FROM tasks")
    fun getTotalTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1")
    fun getCompletedTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 0")
    fun getActiveTaskCount(): Flow<Int>
}
