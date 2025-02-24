package data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // Deletes tasks if the user is deleted
        )
    ],
    indices = [Index("userId")])
data class Task(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val userId: Int // Foreign key referring to User table
)

//enum to represent priorities
enum class Priority {
    Low, Medium, High, Vital
}
