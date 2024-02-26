import AuthorizationSystem.User
import AuthorizationSystem.UserDatabase
import ProxyAccessToDB.Accessor
import ProxyAccessToDB.DishDataBase
import ProxyAccessToDB.Role
import java.util.*
import kotlin.system.exitProcess

fun login(userDatabase: UserDatabase, scanner: Scanner): User? {
    println("Введите логин:")
    val username = scanner.next()
    println("Введите пароль:")
    val password = scanner.next()

    val user = userDatabase.getUser(username)
    return if (user != null && user.checkPassword(password)) {
        user
    } else {
        println("Неверный логин или пароль. Попробуйте снова.")
        null
    }
}

fun register(userDatabase: UserDatabase, scanner: Scanner) {
    println("Введите логин:")
    val username = scanner.next()
    if (userDatabase.getUser(username) != null) {
        println("Пользователь с таким логином уже существует.")
        return
    }

    println("Введите пароль:")
    val password = scanner.next()

    println("1. Посетитель")
    println("2. Администратор")
    println("Выберите тип:")
    var role = Role.Visitor
    when (scanner.nextInt()) {
        1 -> role = Role.Visitor
        2 -> role = Role.Admin
        3 -> return
        else -> println("Некорректный выбор. Попробуйте снова.")
    }

    val user = User(username, "", role)
    user.setPassword(password)
    userDatabase.addUser(user)

    println("Пользователь успешно зарегистрирован.")
}

fun startAuthentification(): User {
    val userDatabase = UserDatabase()

    val scanner = Scanner(System.`in`)
    var loggedInUser: User? = null

    while (loggedInUser == null) {
        println("1. Вход")
        println("2. Регистрация")
        println("3. Выход")
        println("Выберите действие:")
        when (scanner.nextInt()) {
            1 -> loggedInUser = login(userDatabase, scanner)
            2 -> register(userDatabase, scanner)
            3 -> exitProcess(0)
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }
    println("Вы вошли как ${loggedInUser.username}. Тип пользователя - ${if (loggedInUser.role == Role.Visitor) "Посетитель" else "Администратор"}")
    return loggedInUser;
}

fun mainMenu(user: User) {
    val scanner = Scanner(System.`in`)
    val dishDataBase = DishDataBase()
    val accessor = Accessor(dishDataBase, user.role)
    var exit = true;
    while (exit) {
        println("1. Добавить блюдо")
        println("2. Удалить блюдо")
        println("3. Поменять параметр существующего блюда")

        println("6. Выход")
        println("Выберите действие:")
        when (scanner.nextInt()) {
            1 -> accessor.addDish()
            2 -> accessor.removeDish()
            3 -> accessor.changeNumber()
            6 -> exit = false;
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }
    return;
}

fun main(args: Array<String>) {
    val user = startAuthentification()
    mainMenu(user);

}