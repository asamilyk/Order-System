
import AuthorizationSystem.User
import AuthorizationSystem.UserDatabase
import Dish.DishDataBase
import Order.OrderDataBase
import ProxyAccessToDB.Accessor
import ProxyAccessToDB.Role
import ProxyAccessToDB.Service
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
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

    val choice = readLine()?.toIntOrNull()
    when (choice) {
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
    val dishDatabase = DishDataBase()
    val ordersDatabase = OrderDataBase()

    val usersFilePath = "users.json"
    val dishFilePath = "dish.json"

    userDatabase.loadUserData(usersFilePath)
    dishDatabase.loadDishData(dishFilePath)

    val scanner = Scanner(System.`in`)
    var loggedInUser: User? = null

    // Начальное меню входа
    while (true) {
        println("1. Вход")
        println("2. Регистрация")
        println("3. Выход")
        println("Выберите действие:")

        val choice = readLine()?.toIntOrNull()
        when (choice) {
            1 -> {
                loggedInUser = login(userDatabase, scanner)
                if (loggedInUser != null) {
                    mainMenu(loggedInUser, executorService, dishDatabase, ordersDatabase)
                }
            }

            2 -> register(userDatabase, scanner)
            3 -> break;
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }
    userDatabase.saveUserData(usersFilePath)
    dishDatabase.saveDishData(dishFilePath)
}

fun mainMenu(user: User, executorService: ExecutorService, dishDataBase: DishDataBase, orderDataBase: OrderDataBase) {
    val service = Service(dishDataBase, orderDataBase)
    val moneyFilePath = "money.json"

    val money = try {
        val dishJson = File(moneyFilePath).readText()
        Json.decodeFromString<Double>(dishJson)
    } catch (e:Exception) {
        0.0
    }
    service.money = money
    val accessor = Accessor(service, user.role)

    // меню для выбора действия
    while (true) {
        println("0. Посмотреть меню")
        println("1. Создать заказ")
        println("2. Действия с существующими заказами")
        println("3. Выход")
        if (user.role == Role.Admin) {
            println("4. Добавить блюдо")
            println("5. Удалить блюдо")
            println("6. Поменять цену существующего блюда")
            println("7. Поменять количество существующего блюда")
            println("8. Поменять сложность существующего блюда")
            println("9. Посмотреть статистику по заказам и отзывам")
        }

        println("Выберите действие:")
        val choice = readLine()?.toIntOrNull()
        when (choice) {
            0 -> accessor.getListOfDishes()
            1 -> accessor.createOrder(user, executorService)
            2 -> accessor.checkCurrentOrders(user)
            4 -> accessor.addDish()
            5 -> accessor.removeDish()
            6 -> accessor.changePrice()
            7 -> accessor.changeNumber()
            8 -> accessor.changeComplexity()
            9 -> accessor.getStatistics()
            3 -> break
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }

    val moneyJson = Json.encodeToString(service.money)
    File(moneyJson).writeText(moneyJson)
    println("Данные о выручке сохранены в файл: $moneyFilePath")
    service.money
    return;
}

fun main(args: Array<String>) {
    val executorService = Executors.newCachedThreadPool()
    startAuthentification(executorService)
    executorService.shutdown()
}