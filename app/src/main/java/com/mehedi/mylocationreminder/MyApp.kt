package com.mehedi.mylocationreminder

import android.app.Application
import com.mehedi.mylocationreminder.locationreminders.data.ReminderDataSource
import com.mehedi.mylocationreminder.locationreminders.data.local.LocalDB
import com.mehedi.mylocationreminder.locationreminders.data.local.RemindersLocalRepository
import com.mehedi.mylocationreminder.locationreminders.reminderslist.RemindersListViewModel
import com.mehedi.mylocationreminder.locationreminders.savereminder.SaveReminderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - to be later injected into Fragment with dedicated injector using by viewModel()
            viewModel {
                RemindersListViewModel(
                    get(),
                    get() as ReminderDataSource
                )
            }
            //Declare singleton definitions to be later injected using by inject()
            single {
                //This view model is declared singleton to be used across multiple fragments
                SaveReminderViewModel(
                    get(),
                    get() as ReminderDataSource
                )
            }
            
            // Define the ReminderDataSource type with its implementation
            single<ReminderDataSource> { RemindersLocalRepository(get()) }  // <-- Add this line
            
            // Provide the RemindersLocalRepository implementation for ReminderDataSource
            single { RemindersLocalRepository(get()) }
            
            // Provide the LocalDB instance
            single { LocalDB.createRemindersDao(this@MyApp) }
        }
        
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }
    }
}
