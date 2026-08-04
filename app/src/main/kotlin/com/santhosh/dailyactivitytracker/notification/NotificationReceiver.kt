package com.santhosh.dailyactivitytracker.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager = NotificationManager(it)
            when (intent?.action) {
                "com.santhosh.dailyactivitytracker.SHOW_DAILY_SUMMARY" -> {
                    notificationManager.showDailySummaryNotification(0, 0)
                }
            }
        }
    }
}
