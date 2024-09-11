package com.udacity.project4.data

import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result



class FakeDataSource : ReminderDataSource {

    var reminders = mutableListOf<ReminderDTO>()
    var errorMessage: String? = null
    
    private var shouldReturnError = false
    
    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
    
    
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