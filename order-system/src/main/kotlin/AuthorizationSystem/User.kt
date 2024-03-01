package AuthorizationSystem
import ProxyAccessToDB.Role
import org.mindrot.jbcrypt.BCrypt
class User(val username: String, var passwordHash: String, val role: Role) {
    fun setPassword(password: String) {
        passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun checkPassword(password: String): Boolean {
        return BCrypt.checkpw(password, passwordHash)
    }




}