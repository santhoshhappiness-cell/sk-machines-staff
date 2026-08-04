package com.santhosh.dailyactivitytracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santhosh.dailyactivitytracker.data.model.Task
import com.santhosh.dailyactivitytracker.notification.NotificationManager
import com.santhosh.dailyactivitytracker.ui.screens.AddTaskScreen
import com.santhosh.dailyactivitytracker.ui.screens.HomeScreen
import com.santhosh.dailyactivitytracker.ui.theme.DailyActivityTrackerTheme
import com.santhosh.dailyactivitytracker.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var notificationManager: NotificationManager

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                notificationManager.showTaskAddedNotification("Permission granted!")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationManager = NotificationManager(this)

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            DailyActivityTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: TaskViewModel = hiltViewModel()
                    val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()
                    val activeTasks by viewModel.activeTasks.collectAsStateWithLifecycle()
                    val completedTasks by viewModel.completedTasks.collectAsStateWithLifecycle()
                    val activeTaskCount by viewModel.activeTaskCount.collectAsStateWithLifecycle()
                    val completedTaskCount by viewModel.completedTaskCount.collectAsStateWithLifecycle()

                    var showAddTaskScreen by remember { mutableStateOf(false) }
                    val coroutineScope = rememberCoroutineScope()

                    AnimatedVisibility(
                        visible = !showAddTaskScreen,
                        exit = fadeOut(),
                        enter = fadeIn()
                    ) {
                        HomeScreen(
                            activeTasks = activeTasks,
                            completedTasks = completedTasks,
                            onAddTaskClick = { showAddTaskScreen = true },
                            onTaskComplete = { task ->
                                viewModel.toggleTaskCompletion(task)
                                notificationManager.showTaskCompletedNotification(task.title)
                                coroutineScope.launch {
                                    // Small delay for better UX
                                    kotlinx.coroutines.delay(500)
                                }
                            },
                            onTaskDelete = { task ->
                                viewModel.deleteTask(task)
                            },
                            activeTaskCount = activeTaskCount,
                            completedTaskCount = completedTaskCount
                        )
                    }

                    AnimatedVisibility(
                        visible = showAddTaskScreen,
                        exit = fadeOut(),
                        enter = fadeIn()
                    ) {
                        AddTaskScreen(
                            onTaskCreate = { task ->
                                viewModel.addTask(task)
                                notificationManager.showTaskAddedNotification(task.title)
                            },
                            onClose = { showAddTaskScreen = false }
                        )
                    }
                }
            }
        }
    }
}
