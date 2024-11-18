package database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import data.model.Task
import data.model.User

@Database(
    entities = [Task::class,  User::class],
    version = 2,
    exportSchema = false
)
@ConstructedBy(TaskDatabaseConstructor::class)
abstract class TaskDatabase: RoomDatabase(){

    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
}


// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TaskDatabaseConstructor : RoomDatabaseConstructor<TaskDatabase> {
    override fun initialize(): TaskDatabase
}