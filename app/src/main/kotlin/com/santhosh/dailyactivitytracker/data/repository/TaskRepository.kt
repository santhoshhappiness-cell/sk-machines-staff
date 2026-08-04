package com.santhosh.dailyactivitytracker.data.repository

import com.santhosh.dailyactivitytracker.data.dao.TaskDao
import com.santhosh.dailyactivitytracker.data.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getActiveTasks(): Flow<List<Task>> = taskDao.getActiveTasks()

    fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()

    fun getTasksByCategory(category: String): Flow<List<Task>> = taskDao.getTasksByCategory(category)

    fun getTasksByPriority(priority: Int): Flow<List<Task>> = taskDao.getTasksByPriority(priority)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun clearCompletedTasks() = taskDao.clearCompletedTasks()

    fun getTotalTaskCount(): Flow<Int> = taskDao.getTotalTaskCount()

    fun getCompletedTaskCount(): Flow<Int> = taskDao.getCompletedTaskCount()

    fun getActiveTaskCount(): Flow<Int> = taskDao.getActiveTaskCount()
}
