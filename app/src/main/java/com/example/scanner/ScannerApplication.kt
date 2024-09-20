package com.example.scanner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScannerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize global dependencies or services if needed
    }
}