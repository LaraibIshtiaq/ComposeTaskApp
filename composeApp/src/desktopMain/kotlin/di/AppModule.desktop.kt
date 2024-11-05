package di

import database.TaskDatabase
import di.database.getTaskDatabase
import org.koin.dsl.module

actual val platformModule = module {
    // Private TaskDatabase instance, not accessible outside this module
    single { getTaskDatabase() }

    // Public TaskDao instance from TaskDatabase
    single { get<TaskDatabase>().taskDao() }
}