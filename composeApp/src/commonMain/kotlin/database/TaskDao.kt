package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import data.model.Task

@Dao
interface TaskDao {
    ///For inserting or updating a task
    @Upsert
    suspend fun upsertTask(task: Task)

    ///For deleting a task
    @Delete
    suspend fun deleteTask(task: Task)


    @Query("SELECT * FROM task WHERE userId = :userId")
    fun getTasksByUser(userId: Int): Flow<List<Task>>
}