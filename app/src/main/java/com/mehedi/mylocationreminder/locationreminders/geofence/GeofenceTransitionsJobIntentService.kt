package com.mehedi.mylocationreminder.locationreminders.geofence

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.mehedi.mylocationreminder.locationreminders.data.ReminderDataSource
import com.mehedi.mylocationreminder.locationreminders.data.dto.ReminderDTO
import com.mehedi.mylocationreminder.locationreminders.data.dto.Result
import com.mehedi.mylocationreminder.locationreminders.reminderslist.ReminderDataItem
import com.mehedi.mylocationreminder.utils.sendNotification
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context, GeofenceTransitionsJobIntentService::class.java, JOB_ID, intent
            )
        }
    }

    @SuppressLint("VisibleForTests")
    private fun sendNotification(geofences: List<Geofence>) {
        geofences.forEach {
            val requestId = it.requestId
            val remindersLocalRepository: ReminderDataSource by inject()
            CoroutineScope(coroutineContext).launch(SupervisorJob()) {
                val result = remindersLocalRepository.getReminder(requestId)
                if (result is Result.Success<ReminderDTO>) {
                    val reminderDTO = result.data
                    sendNotification(
                        this@GeofenceTransitionsJobIntentService, ReminderDataItem(
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

    override fun onHandleWork(intent: Intent) {
        val event = GeofencingEvent.fromIntent(intent)
        event?.triggeringGeofences?.let { sendNotification(it) }
    }
}
