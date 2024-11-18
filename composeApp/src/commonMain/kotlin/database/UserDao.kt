package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import data.model.Task
import data.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    ///For inserting or updating a task
    @Upsert
    suspend fun addUser(user: User)

    ///For deleting a task
    @Delete
    suspend fun findUserByEmail(email: String)

    ///For getting list of tasks
    @Query("SELECT * FROM user")
    fun getAllTasksOfUser(id: Long): Flow<List<Task>>
}