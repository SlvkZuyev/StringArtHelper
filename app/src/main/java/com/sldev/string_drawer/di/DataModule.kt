package com.sldev.string_drawer.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.sldev.string_drawer.data.SnapshotDao
import com.sldev.string_drawer.data.SnapshotDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): SnapshotDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SnapshotDatabase::class.java, "string-image-db"
        ).build()
    }

    @Provides
    fun provideSnapshotDao(database: SnapshotDatabase): SnapshotDao {
        return database.snapshotDao()
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("string_drawer_prefs", MODE_PRIVATE)
    }

}