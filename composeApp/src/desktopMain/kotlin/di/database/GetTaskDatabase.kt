package di.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.TaskDatabase
import java.io.File

fun getTaskDatabase() : TaskDatabase {

    val dbFile = File(System.getProperty("java.io.tmpdir"), "tasks.db")
    return Room.databaseBuilder<TaskDatabase>(
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}