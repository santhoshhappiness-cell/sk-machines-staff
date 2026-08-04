package com.santhosh.dailyactivitytracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santhosh.dailyactivitytracker.data.model.Task

@Composable
fun AddTaskScreen(
    onTaskCreate: (Task) -> Unit,
    onClose: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("General") }
    var selectedPriority by remember { mutableStateOf(1) }

    val categories = listOf("Work", "Personal", "Health", "Shopping", "Learning", "Other")
    val priorities = listOf(1, 2, 3)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add New Task", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, "Close")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically() + fadeIn()
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Task Title*") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { 50 }) + fadeIn(),
                    delayMillis = 50
                ) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description (optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .animateContentSize(),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 3
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { 100 }) + fadeIn(),
                    delayMillis = 100
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Priority",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            priorities.forEach { priority ->
                                val label = when (priority) {
                                    1 -> "Low"
                                    2 -> "Medium"
                                    3 -> "High"
                                    else -> "Low"
                                }
                                PriorityFilterChip(
                                    label = label,
                                    selected = selectedPriority == priority,
                                    onClick = { selectedPriority = priority },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { 150 }) + fadeIn(),
                    delayMillis = 150
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Category",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.take(3).forEach { category ->
                                CategoryFilterChip(
                                    label = category,
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.drop(3).forEach { category ->
                                CategoryFilterChip(
                                    label = category,
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { 200 }) + fadeIn(),
                    delayMillis = 200
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onClose,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                if (title.isNotEmpty()) {
                                    val newTask = Task(
                                        title = title,
                                        description = description,
                                        category = selectedCategory,
                                        priority = selectedPriority
                                    )
                                    onTaskCreate(newTask)
                                    onClose()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            enabled = title.isNotEmpty()
                        ) {
                            Text("Add Task")
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
fun PriorityFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier.animateContentSize()
    )
}

@Composable
fun CategoryFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = Modifier.animateContentSize()
    )
}
