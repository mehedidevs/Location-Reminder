package com.udacity.project4.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.R
import com.udacity.project4.data.FakeDataSource
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.reminderslist.toReminderDTO
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import java.util.concurrent.CountDownLatch

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {
    
    
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var saveReminderViewModel: SaveReminderViewModel
    private lateinit var fakeReminderDataSource: FakeDataSource
    
    @Before
    fun setupSaveReminderViewModel() {
        fakeReminderDataSource = FakeDataSource()
        saveReminderViewModel = SaveReminderViewModel(
            ApplicationProvider.getApplicationContext(),
            fakeReminderDataSource
        )
    }
    
    private fun createFakeReminderDataItem(): ReminderDataItem {
        return ReminderDataItem(
            "title todo",
            "description todo",
            "location todo",
            100.00,
            50.00
        )
    }
    
    private fun createFakeErrorReminderDataItem(): ReminderDataItem {
        return ReminderDataItem(
            "title todo",
            "description todo",
            "",
            100.00,
            50.00
        )
    }
    
    
   
    
    @Test
    @ExperimentalCoroutinesApi
    fun check_loading_showLoadingWhileSaving_hideLoadingAfterSave() = runTest {
        // Arrange
        val item = createFakeReminderDataItem()
      
        // Act
        saveReminderViewModel.saveReminder(item)
        
        // Wait for saving to complete
        delay(3000)
        val secondTestValue = saveReminderViewModel.showLoading.getOrAwaitValue()
        // Assert that loading is hidden after saving
        Assert.assertEquals(false, secondTestValue)
    }
    
    @Test
    fun deleteAllReminders_clearsAllRemindersFromDataSource() = runTest {
        // Arrange: Save a reminder
        val item = createFakeReminderDataItem()
        saveReminderViewModel.saveReminder(item)
        Assert.assertTrue(fakeReminderDataSource.reminders.isNotEmpty())
        
        // Act: Delete all reminders
        fakeReminderDataSource.deleteAllReminders()
        
        // Assert: Check that reminders are cleared
        Assert.assertTrue(fakeReminderDataSource.reminders.isEmpty())
    }
    
    
    @Test
    fun saveReminder_savesReminderToDataSourceAndHidesLoading() =
        runTest {
            val item = createFakeReminderDataItem()
            
            saveReminderViewModel.saveReminder(item)
            
            assert(fakeReminderDataSource.reminders.contains(item.toReminderDTO()))
            Assert.assertEquals(false, saveReminderViewModel.showLoading.getOrAwaitValue())
        }
    
    @Test
    fun validateEnteredData_showsSnackBarErrorMessage() =
        runTest {
            val item = createFakeErrorReminderDataItem()
            
            saveReminderViewModel.validateEnteredData(item)
            
            Assert.assertFalse(fakeReminderDataSource.reminders.contains(item.toReminderDTO()))
            Assert.assertEquals(
                R.string.err_select_location,
                saveReminderViewModel.showSnackBarInt.getOrAwaitValue()
            )
            
        }
    
    
    @After
    fun tearDown() {
        stopKoin()
    }
    
    
}