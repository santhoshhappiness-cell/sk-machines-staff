package com.santhosh.dailyactivitytracker.notification

import android.app.NotificationChannel
import android.app.NotificationManager as SystemNotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.santhosh.dailyactivitytracker.R
import java.util.concurrent.TimeUnit

class NotificationManager(private val context: Context) {

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as SystemNotificationManager

            // Task reminder channel
            val reminderChannel = NotificationChannel(
                CHANNEL_TASK_REMINDER,
                "Task Reminders",
                SystemNotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for task reminders"
                enableVibration(true)
            }
            manager.createNotificationChannel(reminderChannel)

            // Task completed channel
            val completedChannel = NotificationChannel(
                CHANNEL_TASK_COMPLETED,
                "Task Completed",
                SystemNotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications when tasks are completed"
            }
            manager.createNotificationChannel(completedChannel)

            // Daily summary channel
            val summaryChannel = NotificationChannel(
                CHANNEL_DAILY_SUMMARY,
                "Daily Summary",
                SystemNotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Daily activity summary"
            }
            manager.createNotificationChannel(summaryChannel)
        }
    }

    fun showTaskAddedNotification(taskTitle: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_TASK_COMPLETED)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task Added")
            .setContentText(taskTitle)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as SystemNotificationManager
        notificationManager.notify(NOTIFICATION_ID_TASK_ADDED, notification)
    }

    fun showTaskCompletedNotification(taskTitle: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_TASK_COMPLETED)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task Completed! 🎉")
            .setContentText(taskTitle)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as SystemNotificationManager
        notificationManager.notify(NOTIFICATION_ID_TASK_COMPLETED, notification)
    }

    fun showDailySummaryNotification(totalCompleted: Int, totalPending: Int) {
        val summary = "Completed: $totalCompleted tasks | Pending: $totalPending tasks"
        val notification = NotificationCompat.Builder(context, CHANNEL_DAILY_SUMMARY)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Daily Activity Summary")
            .setContentText(summary)
            .setStyle(NotificationCompat.BigTextStyle().bigText(summary))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as SystemNotificationManager
        notificationManager.notify(NOTIFICATION_ID_DAILY_SUMMARY, notification)
    }

    fun scheduleDailySummary(hour: Int = 20, minute: Int = 0) {
        val dailySummaryWork = PeriodicWorkRequestBuilder<DailyTaskSummaryWorker>(
            1, TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_summary",
            ExistingPeriodicWorkPolicy.KEEP,
            dailySummaryWork
        )
    }

    companion object {
        const val CHANNEL_TASK_REMINDER = "task_reminder"
        const val CHANNEL_TASK_COMPLETED = "task_completed"
        const val CHANNEL_DAILY_SUMMARY = "daily_summary"

        const val NOTIFICATION_ID_TASK_ADDED = 1
        const val NOTIFICATION_ID_TASK_COMPLETED = 2
        const val NOTIFICATION_ID_DAILY_SUMMARY = 3
    }
}
