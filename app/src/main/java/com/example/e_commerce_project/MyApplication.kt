package com.example.e_commerce_project

import android.app.Application
import android.util.Log

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("Tag", "My Application on create")
    }
}