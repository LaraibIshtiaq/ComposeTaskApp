package database

import androidx.room.Dao
import androidx.room.Upsert
import data.model.User


@Dao
interface UserDao {
    ///For inserting or updating a task
    @Upsert
    suspend fun addUser(user: User)

//    ///For deleting a task
//    @Delete
//    suspend fun findUserByEmail(email: String)
}