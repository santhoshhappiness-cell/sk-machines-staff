package com.santhosh.dailyactivitytracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santhosh.dailyactivitytracker.data.model.Task
import com.santhosh.dailyactivitytracker.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks.asStateFlow()

    private val _activeTasks = MutableStateFlow<List<Task>>(emptyList())
    val activeTasks: StateFlow<List<Task>> = _activeTasks.asStateFlow()

    private val _completedTasks = MutableStateFlow<List<Task>>(emptyList())
    val completedTasks: StateFlow<List<Task>> = _completedTasks.asStateFlow()

    private val _activeTaskCount = MutableStateFlow(0)
    val activeTaskCount: StateFlow<Int> = _activeTaskCount.asStateFlow()

    private val _completedTaskCount = MutableStateFlow(0)
    val completedTaskCount: StateFlow<Int> = _completedTaskCount.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { tasks ->
                _allTasks.value = tasks
            }
        }

        viewModelScope.launch {
            repository.getActiveTasks().collect { tasks ->
                _activeTasks.value = tasks
            }
        }

        viewModelScope.launch {
            repository.getCompletedTasks().collect { tasks ->
                _completedTasks.value = tasks
            }
        }

        viewModelScope.launch {
            repository.getActiveTaskCount().collect { count ->
                _activeTaskCount.value = count
            }
        }

        viewModelScope.launch {
            repository.getCompletedTaskCount().collect { count ->
                _completedTaskCount.value = count
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        val updatedTask = task.copy(
            isCompleted = !task.isCompleted,
            completedAt = if (!task.isCompleted) java.time.LocalDateTime.now().toString() else null
        )
        updateTask(updatedTask)
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            repository.clearCompletedTasks()
        }
    }
}
