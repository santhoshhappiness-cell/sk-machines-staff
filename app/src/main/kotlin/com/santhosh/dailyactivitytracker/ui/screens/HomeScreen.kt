package com.santhosh.dailyactivitytracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.santhosh.dailyactivitytracker.data.model.Task
import com.santhosh.dailyactivitytracker.ui.components.TaskCard
import com.santhosh.dailyactivitytracker.ui.theme.getPriorityColor

@Composable
fun HomeScreen(
    activeTasks: List<Task>,
    completedTasks: List<Task>,
    onAddTaskClick: () -> Unit,
    onTaskComplete: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
    activeTaskCount: Int,
    completedTaskCount: Int
) {
    var showCompletedTasks by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize()
            ) {
                Icon(Icons.Default.Add, "Add Task")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Daily Activity Tracker",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Stats Card
            item {
                StatisticsCard(
                    activeCount = activeTaskCount,
                    completedCount = completedTaskCount
                )
            }

            // Active Tasks Section
            if (activeTasks.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Active Tasks",
                        count = activeTasks.size
                    )
                }

                items(activeTasks) { task ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInHorizontally() + fadeIn(),
                        exit = slideOutHorizontally() + fadeOut()
                    ) {
                        TaskCard(
                            task = task,
                            onComplete = { onTaskComplete(task) },
                            onDelete = { onTaskDelete(task) }
                        )
                    }
                }
            }

            // Completed Tasks Section
            if (completedTasks.isNotEmpty()) {
                item {
                    CompletedTasksToggle(
                        isExpanded = showCompletedTasks,
                        count = completedTasks.size,
                        onToggle = { showCompletedTasks = !showCompletedTasks }
                    )
                }

                if (showCompletedTasks) {
                    items(completedTasks) { task ->
                        AnimatedVisibility(
                            visible = showCompletedTasks,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            TaskCard(
                                task = task,
                                isCompleted = true,
                                onComplete = { onTaskComplete(task) },
                                onDelete = { onTaskDelete(task) }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatisticsCard(
    activeCount: Int,
    completedCount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(label = "Active", value = activeCount.toString())
            Divider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
            )
            StatItem(label = "Completed", value = completedCount.toString())
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun SectionHeader(title: String, count: Int) {
    Text(
        text = "$title ($count)",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
fun CompletedTasksToggle(
    isExpanded: Boolean,
    count: Int,
    onToggle: () -> Unit
) {
    TextButton(
        onClick = onToggle,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Text(
            text = "${if (isExpanded) "▼" else "▶"} Completed ($count)",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
