package database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getTaskDatabase(context: Context): TaskDatabase{

    val dbFile = context.getDatabasePath("task.db")
    return Room.databaseBuilder<TaskDatabase>(
        context= context.applicationContext,
        name= dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}