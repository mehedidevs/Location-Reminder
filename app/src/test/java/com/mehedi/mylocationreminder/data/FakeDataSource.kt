package com.mehedi.mylocationreminder.data

import com.mehedi.mylocationreminder.locationreminders.data.ReminderDataSource
import com.mehedi.mylocationreminder.locationreminders.data.dto.ReminderDTO
import com.mehedi.mylocationreminder.locationreminders.data.dto.Result



class FakeDataSource : ReminderDataSource {

    var reminders = mutableListOf<ReminderDTO>()
    var errorMessage: String? = null

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (errorMessage != null) {
            return Result.Error(errorMessage)
        } else {
            reminders.let { return Result.Success(it) }
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        reminders.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error("Reminder not found")
    }

    override suspend fun deleteAllReminders() {
        reminders = mutableListOf()
    }
    
    
}