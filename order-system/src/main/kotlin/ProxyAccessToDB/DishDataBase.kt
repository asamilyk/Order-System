package ProxyAccessToDB

import AuthorizationSystem.User
import Dish.Dish
import Dish.DishDataBaseService
import Order.Order
import Order.OrderDataBase
import Order.OrderStatus
import java.util.*
import java.util.concurrent.ExecutorService

class DishDataBase() : ServiceInterface {
    var dishDb = DishDataBaseService()
    var orderDb = OrderDataBase()
    val scanner = Scanner(System.`in`)

    override fun createOrder(user: User,executorService: ExecutorService) {
        getListOfDishes()
        val curDishes = mutableListOf<Dish>()
        println("Введите количество блюд, которое вы хотите добавить в заказ")
        val number = scanner.nextInt()
        println("Введите номера блюд, которые вы хотите добавить в заказ")
        for(i in 1..number){
            val id = scanner.nextInt()
            if (id < dishDb.getListOfDishes().size){
                curDishes.add(dishDb.getListOfDishes()[id])
            }
            else{
                throw Exception("no dish with this id")
            }
        }
        val order = Order(curDishes, OrderStatus.processing, user)
        orderDb.addOrder(order)
        val t = executorService.submit(
            Runnable {
                order.cooking()
            }
        )

    }

    override fun checkCurrentOrders(user: User) {
        println("Список ваших заказов:")
        for(order in orderDb.getListOfOrders()){
            if (order.User == user){
                println(order)
            }
        }
        var exit = true;
        while (exit) {
            println("1. Вернуться в меню")
            println("2. Отменить заказ")
            println("3. Добавить блюдо в существующий заказ")
            println("Выберите действие:")

            when (scanner.nextInt()) {
                1 -> return
                2 -> return
                3 -> exit = false
            }
        }
    }

    override fun getListOfDishes() {
        val listOfDishes = dishDb.getListOfDishes()
        println("Список блюд:")

        for (dish in listOfDishes) {
            println(dish)
        }
    }

    override fun addDish() {
        println("Введите название блюда")
        val name = scanner.next()
        println("Введите количество для блюда")
        val number = scanner.nextInt()
        println("Введите цену для блюда")
        val price = scanner.nextFloat()
        println("Введите сложность для блюда")
        val complexity = scanner.nextInt()
        dishDb.addDish(Dish(name, number, price, complexity))
    }

    override fun removeDish() {
        println("Введите номер блюда для удаления")
        getListOfDishes()
        val id = scanner.nextInt()
        if (id < dishDb.getListOfDishes().size) {
            dishDb.removeDish(id)
        } else {
            throw Exception("no element with this id")
        }
    }

    override fun changePrice() {
        println("Введите номер блюда для изменения цены")
        getListOfDishes()
        val id = scanner.nextInt()
        println("Введите новую цену для блюда")
        getListOfDishes()
        val price = scanner.nextFloat()
        if (id < dishDb.getListOfDishes().size) {
            dishDb.changePrice(id, price)
        } else {
            throw Exception("no dish with this id")
        }
    }

    override fun changeComplexity() {
        println("Введите номер блюда для изменения сложности приготовления")
        getListOfDishes()
        val id = scanner.nextInt()
        println("Введите новую сложность для блюда")
        getListOfDishes()
        val complexity = scanner.nextInt()
        if (id < dishDb.getListOfDishes().size) {
            dishDb.changeComplexity(id, complexity)
        } else {
            throw Exception("no dish with this id")
        }
    }

    override fun changeNumber() {
        println("Введите номер блюда для изменения количества")
        getListOfDishes()
        val id = scanner.nextInt()
        println("Введите новое количество для блюда")
        getListOfDishes()
        val number = scanner.nextInt()
        if (id < dishDb.getListOfDishes().size) {
            dishDb.changeNumber(id, number)
        } else {
            throw Exception("no dish with this id")
            //return "ERROR: no user or album"
        }
    }
}