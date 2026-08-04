package com.santhosh.dailyactivitytracker.di

import android.content.Context
import com.santhosh.dailyactivitytracker.data.db.TaskDatabase
import com.santhosh.dailyactivitytracker.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return TaskDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskRepository(database: TaskDatabase): TaskRepository {
        return TaskRepository(database.taskDao())
    }
}
