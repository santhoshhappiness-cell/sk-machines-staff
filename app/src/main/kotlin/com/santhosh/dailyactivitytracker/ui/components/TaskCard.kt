package com.santhosh.dailyactivitytracker.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.santhosh.dailyactivitytracker.data.model.Task
import com.santhosh.dailyactivitytracker.ui.theme.getPriorityColor

@Composable
fun TaskCard(
    task: Task,
    isCompleted: Boolean = false,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    var isBeingDeleted by remember { mutableStateOf(false) }
    var scaleAnimated by remember { mutableStateOf(1f) }

    AnimatedVisibility(
        visible = !isBeingDeleted,
        exit = scaleOut(targetScale = 0.8f) + fadeOut(),
        modifier = Modifier.animateContentSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .scale(scaleAnimated)
                .animateContentSize()
                .clickable { onComplete() },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isCompleted) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isCompleted) 2.dp else 4.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Task Info
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    if (task.description.isNotEmpty()) {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }

                    // Priority and Category
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PriorityBadge(priority = task.priority)
                        CategoryBadge(category = task.category)
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Complete Button
                    IconButton(
                        onClick = {
                            scaleAnimated = 0.95f
                            onComplete()
                        },
                        modifier = Modifier.animateContentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Complete",
                            tint = if (isCompleted) {
                                MaterialTheme.colorScheme.secondary
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier.scale(
                                if (isCompleted) 1.2f else 1f
                            )
                        )
                    }

                    // Delete Button
                    IconButton(
                        onClick = {
                            isBeingDeleted = true
                            onDelete()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Int) {
    val (color, label) = when (priority) {
        1 -> Pair(0xFF90EE90, "Low")
        2 -> Pair(0xFFFFD700, "Medium")
        3 -> Pair(0xFFFF6B6B, "High")
        else -> Pair(0xFF90EE90, "Low")
    }

    Surface(
        modifier = Modifier
            .height(24.dp)
            .padding(0.dp),
        shape = RoundedCornerShape(4.dp),
        color = androidx.compose.ui.graphics.Color(color)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun CategoryBadge(category: String) {
    Surface(
        modifier = Modifier
            .height(24.dp)
            .padding(0.dp),
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}
