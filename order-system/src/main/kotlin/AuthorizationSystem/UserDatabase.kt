package AuthorizationSystem

class UserDatabase {
    private val users = HashMap<String, User>()

    fun addUser(user: User) {
        users[user.username] = user
    }

    fun getUser(username: String): User? {
        return users[username]
    }
}