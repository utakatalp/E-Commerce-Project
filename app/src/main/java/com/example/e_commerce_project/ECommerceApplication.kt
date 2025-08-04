package com.example.e_commerce_project

import android.app.Application
import com.example.e_commerce_project.data.AppContainer
import com.example.e_commerce_project.data.DefaultAppContainer

class ECommerceApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()

    }

}