package AuthorizationSystem

import ProxyAccessToDB.Role
import kotlinx.serialization.Serializable
import java.security.MessageDigest
@Serializable
class User(val username: String, var passwordHash: String, val role: Role) {
    fun setPassword(password: String) {
        passwordHash = encryptPassword(password)
    }

    fun checkPassword(password: String): Boolean {
        val inputPasswordHash = encryptPassword(password)
        return passwordHash == inputPasswordHash
    }

    private fun encryptPassword(password: String): String {
        val bytes = password.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(bytes)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}