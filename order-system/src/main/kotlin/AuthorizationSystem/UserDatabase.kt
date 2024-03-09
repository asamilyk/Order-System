package AuthorizationSystem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class UserDatabase {
    private var users = HashMap<String, User>()

    fun addUser(user: User) {
        users[user.username] = user
    }

    fun getUser(username: String): User? {
        return users[username]
    }

    // Функция для сохранения данных о пользователях в JSON файлы
    fun saveUserData(usersFilePath: String) {
        val usersJson = Json.encodeToString(users)
        File(usersFilePath).writeText(usersJson)
        println("Данные о пользователях сохранены в файл: $usersFilePath")
    }

    // Функция для загрузки данных о пользователях из JSON файлов
    fun loadUserData(usersFilePath: String) {
        return try {
            val usersJson = File(usersFilePath).readText()
            val movies = Json.decodeFromString<HashMap<String, User>>(usersJson)
        } catch (e: Exception) {
            users = HashMap<String, User>()
        }
    }
}