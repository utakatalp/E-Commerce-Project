package com.example.e_commerce_project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commerce_project.data.local.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
}
