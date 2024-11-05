package database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(TaskDatabaseConstructor::class)
abstract class TaskDatabase: RoomDatabase(){

    abstract fun taskDao(): TaskDao
}


// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TaskDatabaseConstructor : RoomDatabaseConstructor<TaskDatabase> {
    override fun initialize(): TaskDatabase
}