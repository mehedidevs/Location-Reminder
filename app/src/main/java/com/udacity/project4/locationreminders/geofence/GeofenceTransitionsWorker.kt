package com.udacity.project4.locationreminders.geofence


import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.utils.sendNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GeofenceTransitionsWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {
    
    private val remindersLocalRepository: ReminderDataSource by inject()
    
    override suspend fun doWork(): Result {
        val intent = inputData.getString("intent")?.let { Intent(it) }
        if (intent != null) {
            val event = GeofencingEvent.fromIntent(intent)
            event?.triggeringGeofences?.let { geofences ->
                sendNotification(geofences)
            }
        }
        return Result.success()
    }
    
    private suspend fun sendNotification(geofences: List<Geofence>) = withContext(Dispatchers.IO) {
        geofences.forEach { geofence ->
            val requestId = geofence.requestId
            val remindersLocalRepository: ReminderDataSource by inject()
            CoroutineScope(coroutineContext).launch(SupervisorJob()) {
                val result = remindersLocalRepository.getReminder(requestId)
                if (result is com.udacity.project4.locationreminders.data.dto.Result.Success<ReminderDTO>) {
                    val reminderDTO = result.data
                    sendNotification(
                        applicationContext, ReminderDataItem(
                            reminderDTO.title,
                            reminderDTO.description,
                            reminderDTO.location,
                            reminderDTO.latitude,
                            reminderDTO.longitude,
                            reminderDTO.id
                        )
                    )
                }
            }
            
        }
    }
    
}
