package di

import database.TaskDatabase
import database.getTaskDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    // Private TaskDatabase instance, not accessible outside this module
    single { getTaskDatabase(androidContext()) } bind TaskDatabase::class

    // Public TaskDao instance from TaskDatabase
    single { get<TaskDatabase>().taskDao() }
}