package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val title: String,
    val description: String,
    val priority: Priority
)

//enum to represent priorities
enum class Priority {
    Low, Medium, High, Vital
}
