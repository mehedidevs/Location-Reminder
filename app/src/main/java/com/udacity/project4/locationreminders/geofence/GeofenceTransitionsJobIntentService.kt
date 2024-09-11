package com.udacity.project4.locationreminders.geofence

import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class GeofenceTransitionsJobIntentService {
    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            val inputData = Data.Builder()
                .putString("intent", intent.toUri(Intent.URI_INTENT_SCHEME))
                .build()
            
            val workRequest = OneTimeWorkRequestBuilder<GeofenceTransitionsWorker>()
                .setInputData(inputData)
                .build()
            
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
    
    
}
