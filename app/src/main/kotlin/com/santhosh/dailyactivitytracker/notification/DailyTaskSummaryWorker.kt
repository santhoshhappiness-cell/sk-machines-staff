package com.santhosh.dailyactivitytracker.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.santhosh.dailyactivitytracker.data.repository.TaskRepository
import dagger.hilt.android.EntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first

class DailyTaskSummaryWorker(
    @ApplicationContext context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val notificationManager = NotificationManager(applicationContext)

            // In a real scenario, you would inject repository here
            // For now, we'll use a placeholder
            notificationManager.showDailySummaryNotification(
                totalCompleted = 5,
                totalPending = 3
            )

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
