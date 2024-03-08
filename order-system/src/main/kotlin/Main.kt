import AuthorizationSystem.User
import AuthorizationSystem.UserDatabase
import Dish.Dish
import ProxyAccessToDB.Accessor
import ProxyAccessToDB.Role
import ProxyAccessToDB.Service
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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

fun startAuthentification(executorService: ExecutorService) {
    val userDatabase = UserDatabase()
    val usersFilePath = "movies.json"
    userDatabase.loadUserData(usersFilePath)
    val scanner = Scanner(System.`in`)
    var loggedInUser: User? = null
    var exit = true

    var user = User("1", "1", Role.Visitor)
    user.setPassword("1")
    userDatabase.addUser(user)

    while (exit) {
        println("1. Вход")
        println("2. Регистрация")
        println("3. Выход")
        println("Выберите действие:")
        when (scanner.nextInt()) {
            1 -> {
                loggedInUser = login(userDatabase, scanner)
                if (loggedInUser != null) {
                    mainMenu(loggedInUser, executorService)
                }
            }

            2 -> register(userDatabase, scanner)
            3 -> exit = false;
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }
    userDatabase.saveUserData(usersFilePath)
}

fun mainMenu(user: User, executorService: ExecutorService) {
    val scanner = Scanner(System.`in`)
    val dishDataBase = Service()
    val accessor = Accessor(dishDataBase, user.role)
    var exit = true;
    var dish1 = Dish("a", 1, 1.0f, 1)
    var dish2 = Dish("b", 1, 1.0f, 1)
    dishDataBase.dishDb.dishes.add(dish1)
    dishDataBase.dishDb.dishes.add(dish2)
    while (exit) {
        println("1. Создать заказ")
        println("2. Действия с существующими заказами")
        println("3. Выход")
        if (user.role == Role.Admin) {
            println("4. Добавить блюдо")
            println("5. Удалить блюдо")
            println("6. Поменять цену существующего блюда")
            println("7. Поменять количество существующего блюда")
            println("8. Поменять сложность существующего блюда")
        }

        println("Выберите действие:")

        when (scanner.nextInt()) {
            1 -> accessor.createOrder(user, executorService)
            2 -> accessor.checkCurrentOrders(user)
            4 -> accessor.addDish()
            5 -> accessor.removeDish()
            6 -> accessor.changePrice()
            7 -> accessor.changeNumber()
            8 -> accessor.changeComplexity()
            3 -> exit = false
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }
    return;
}

fun main(args: Array<String>) {
    val executorService = Executors.newCachedThreadPool()
    startAuthentification(executorService)
    executorService.shutdown()
}