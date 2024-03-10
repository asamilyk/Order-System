package ProxyAccessToDB

import AuthorizationSystem.User
import Dish.Dish
import Dish.DishDataBase
import InfoForAdmin.Feedback
import InfoForAdmin.Statistics
import Order.Order
import Order.OrderDataBase
import Order.OrderStatus
import java.util.concurrent.ExecutorService


class Service(dishDb: DishDataBase, orderDb: OrderDataBase) : ServiceInterface {
    var DishDb = dishDb
    var OrderDb = orderDb
    var statisticsService = Statistics()
    var feedbackService = Feedback()
    var money = 0.0;
    override fun getListOfDishes() {
        val listOfDishes = DishDb.getListOfDishes()
        println("Список блюд:")

        var i = 1
        for (dish in listOfDishes) {
            print("$i. ")
            println(dish)
            i++
        }
        println("---------------------------------------------------")
    }

    override fun addDish() {
        println("Введите название блюда")
        val name = readLine().toString()
        println("Введите количество для блюда")

        val number = readLine()?.toIntOrNull()
        if (number == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }

        println("Введите цену для блюда")
        val price = readLine()?.toDoubleOrNull()
        if (price == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }

        println("Введите сложность приготовления для блюда")
        val complexity = readLine()?.toIntOrNull()
        if (complexity == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        println("Блюдо успешно добавлено")
        println("-----------------------")
        DishDb.addDish(Dish(name, number, price, complexity))
    }

    override fun removeDish() {
        println("Введите номер блюда для удаления")
        getListOfDishes()
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.removeDish(id)
            println("Блюдо удалено")
            println("-------------")
        } else {
            println("Блюда с таким номером не существует")
            println("-----------------------------------")
        }
    }


    override fun changePrice() {
        println("Введите номер блюда для изменения цены")
        getListOfDishes()
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        println("Введите новую цену для блюда")
        val price = readLine()?.toDoubleOrNull()
        if (price == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.changePrice(id, price)
            println("Цена изменена")
            println("-------------")
        } else {
            println("Блюда с таким номером не существует")
            println("-----------------------------------")
        }
    }

    override fun changeComplexity() {
        getListOfDishes()
        println("Введите номер блюда для изменения сложности приготовления")
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        println("Введите новую сложность для блюда")
        val complexity = readLine()?.toIntOrNull()
        if (complexity == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.changeComplexity(id, complexity)
            println("Сложность изменена")
            println("------------------")
        } else {
            println("Блюда с таким номером не существует")
            println("-----------------------------------")
        }
    }

    override fun changeNumber() {
        getListOfDishes()
        println("Введите номер блюда для изменения количества")
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        println("Введите новое количество для блюда")
        val number = readLine()?.toIntOrNull()
        if (number == null) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.changeNumber(id, number)
            println("Количество изменено")
            println("-------------------")
        } else {
            println("Блюдо с таким номером не существует")
            println("-----------------------------------")
        }
    }

    override fun createOrder(user: User, executorService: ExecutorService) {
        getListOfDishes()
        val curDishes = mutableListOf<Dish>()
        println("Введите количество блюд, которое вы хотите добавить в заказ")
        val number = readLine()?.toIntOrNull()
        if (number == null) {
            println("Некорректный выбор.")
            println("-------------------")
            return
        }
        println("Введите через пробел номера блюд, которые вы хотите добавить в заказ")
        for (i in 1..number) {
            val id = readLine()?.toIntOrNull()
            if (id == null) {
                println("Некорректный выбор.")
                println("-------------------")
                return
            }
            if (id <= DishDb.getListOfDishes().size) {
                if (DishDb.getListOfDishes()[id - 1].number > 0) {
                    curDishes.add(DishDb.getListOfDishes()[id - 1])
                    DishDb.getListOfDishes()[id - 1].complexity--;
                } else {
                    println("Блюдо $id закочилось")
                    println("--------------------")
                    return
                }
            } else {
                println("Некорректный выбор.")
                println("-------------------")
                return;
            }
        }
        val order = Order(curDishes, OrderStatus.processing, user)
        OrderDb.addOrder(order)
        statisticsService.plusOrder(order)
        executorService.submit(
            Runnable {
                order.cooking(OrderDb)
            }
        )

    }

    override fun checkCurrentOrders(user: User) {
        println("Список ваших заказов:")
        val orders = OrderDb.getListOfUserOrders(user)
        if (orders.isEmpty()) {
            println("У вас пока нет заказов")
            println("----------------------")
            return
        } else {
            var i = 1
            for (order in orders) {
                print("$i. ")
                println(order)
                i++
            }
        }
        println("----------------------------------")
        while (true) {
            println("1. Вернуться в меню")
            println("2. Отменить заказ")
            println("3. Добавить блюдо в существующий заказ")
            println("4. Оплатить заказ")
            println("Выберите действие:")
            val choice = readLine()?.toIntOrNull()
            when (choice) {
                1 -> return
                2 -> removeOrder(user)
                3 -> addDishToOrder(user);
                4 -> payOrder(user);
                else -> return;
            }
        }
    }


    fun payOrder(user: User) {
        println("Список ваших заказов, доступных для оплаты:")
        val orders = OrderDb.getListOfUserOrders(user).filter { it.status == OrderStatus.ready }
        if (orders.isEmpty()) {
            println("У вас пока нет таких заказов")
            return
        } else {
            var i = 1
            for (order in orders) {
                print("$i. ")
                println(order)
                i++
            }
        }
        println("----------------------------------")
        println("Введите номер заказа для оплаты")
        val id = readLine()?.toIntOrNull()
        if (id == null || id > OrderDb.getListOfOrders().size) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        val order = orders[id - 1]
        order.status = OrderStatus.paid;
        println("Оплата прошла успешно, списано ${order.cost} р.")
        println("-----------------------------------------------")
        money += order.cost;

        println("Для того, чтобы оставить отзыв, введите 1, для выхода введите любое другое значение")
        val answer = readLine()
        if (answer === "1") {
            feedback(order)
        }

    }

    fun feedback(order: Order) {
        println("Напишите пару слов о заказе и нажмите enter")
        val message = readLine().toString()
        println("Введите число от 1 до 5 - оценка заказа")
        val eval = readLine()?.toIntOrNull()
        if (eval == null || eval > 5 || eval < 1) {
            println("Некоректный ввод, отзыв не оставлен")
        } else {
            feedbackService.addFeedback(eval, message, order)
            println("Спасибо, отзыв оставлен")
        }
    }

    fun addDishToOrder(user: User) {
        println("Введите номер заказа для добавления блюда")
        val id = readLine()?.toIntOrNull()
        if (id == null || id > OrderDb.getListOfUserOrders(user).size) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }
        val order = OrderDb.getOrder(id)
        if (id <= order.dishes.size) {
            if (order.status == OrderStatus.processing) {
                getListOfDishes()
                println("Введите номер блюда для добавления блюда")
                val dishId = readLine()?.toIntOrNull()
                if (dishId == null) {
                    println("Некорректный ввод")
                    println("-----------------")
                    return
                }
                if (dishId >= DishDb.dishes.size) {
                    println("Блюда с таким номером не существует")
                    println("-----------------------------------")
                    return

                }
                val dish = DishDb.dishes[dishId - 1]
                val order = OrderDb.getListOfUserOrders(user)[id - 1]
                if (dish.number > 0) {
                    OrderDb.addDish(order, dish)
                    println("Блюдо добавлено")
                    println("---------------")
                } else {
                    println("Блюдо закончилось")
                    println("-----------------")
                    return
                }
            } else {
                println("Заказ уже начали готовить, добавить блюда нельзя")
                println("------------------------------------------------")
            }

        } else {
            println("Заказа с таким номером не существует")
            println("------------------------------------")
        }
    }


    fun removeOrder(user: User) {
        println("Список ваших заказов:")
        val orders = OrderDb.getListOfUserOrders(user)
        if (orders.isEmpty()) {
            println("У вас пока нет заказов")
            return
        } else {
            var i = 1
            for (order in orders) {
                print("$i. ")
                println(order)
                i++
            }
        }
        println("-----------------------------------")
        println("Введите номер заказа для удаления")
        val id = readLine()?.toIntOrNull()
        if (id == null || id > OrderDb.getListOfOrders().size) {
            println("Некорректный ввод")
            println("-----------------")
            return
        }

        val order = OrderDb.getOrder(id)
        if (id <= order.dishes.size) {
            if (order.status == OrderStatus.processing || order.status == OrderStatus.preparing) {
                OrderDb.removeOrder(id)
                println("Заказ удален")
                println("------------")
            } else {
                println("Заказ уже готов, отменить нельзя")
                println("--------------------------------")
                return
            }
        } else {
            println("Заказа с таким номером не существует")
            println("------------------------------------")
        }
    }

    override fun getStatistics() {
        println("Общее количество заказов: ${statisticsService.numberOfOrders}")
        println("Среднее количество блюд в заказе: ${statisticsService.everageNumberOfDishes}")
        println("Средняя стоимость заказа: ${statisticsService.everageCostOfDishes}")
        println("Средняя оценка заказов: ${feedbackService.everageEval}")
        println("--------------------------------------------")
    }

}