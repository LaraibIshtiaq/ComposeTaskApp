package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey /*autoGenerate = true)*/ val userId: Long,
    val name: String,
    val email: String,
)