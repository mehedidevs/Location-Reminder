package com.udacity.project4.locationreminders.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */
private const val ACTION_GEOFENCE_EVENT =
    "SaveReminderFragment.project4.action.ACTION_GEOFENCE_EVENT"

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        
        Log.d("GeofenceBroadcastReceiver", "Geofence event received")
        
        // Call enqueueWork to handle the geofence transition in the WorkManager
        GeofenceTransitionsJobIntentService.enqueueWork(context, intent)
        
    }
}