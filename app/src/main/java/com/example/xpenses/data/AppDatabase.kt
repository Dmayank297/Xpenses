package com.example.xpenses.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.xpenses.models.Expenses


@Database(entities = [Expenses::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao

}