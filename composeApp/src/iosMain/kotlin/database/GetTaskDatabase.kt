package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

//fun getTaskDatabase(): TaskDatabase{
//
//    val dbFile = NSHomeDirectory() * "/task.db"
//    return Room.databaseBuilder<TaskDatabase>(
//        name = dbFile,
//        factory = { TaskDatabase::class, instantiateImpl() }
//    )
//        .setDriver(BundledSQLiteDriver())
//        .build()
//}


fun getTaskDatabase(): TaskDatabase {
    val dbFilePath = documentDirectory() + "/task.db"
    return Room.databaseBuilder<TaskDatabase>(
        name = dbFilePath,
    ).setDriver(BundledSQLiteDriver())
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}