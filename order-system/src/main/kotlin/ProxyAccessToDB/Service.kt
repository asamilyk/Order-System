package ProxyAccessToDB

import AuthorizationSystem.User
import Dish.Dish
import Dish.DishDataBase
import Order.Order
import Order.OrderDataBase
import Order.OrderStatus
import java.util.concurrent.ExecutorService

class Service(dishDb: DishDataBase, orderDb: OrderDataBase) : ServiceInterface {
    var DishDb = dishDb
    var OrderDb = orderDb

    override fun createOrder(user: User, executorService: ExecutorService) {
        getListOfDishes()
        val curDishes = mutableListOf<Dish>()
        println("Введите количество блюд, которое вы хотите добавить в заказ")
        val number = readLine()?.toIntOrNull()
        if (number == null) {
            println("Некорректный выбор.")
            return
        }
        println("Введите через пробел номера блюд, которые вы хотите добавить в заказ")
        var flag: Boolean = true
        for (i in 1..number) {
            val id = readLine()?.toIntOrNull()
            if (id == null) {
                println("Некорректный выбор. Попробуйте снова.")
                flag = false;
                break;
            }
            if (id <= DishDb.getListOfDishes().size) {
                if (DishDb.getListOfDishes()[id - 1].number > 0) {
                    curDishes.add(DishDb.getListOfDishes()[id - 1])
                    DishDb.getListOfDishes()[id - 1].complexity--;
                } else {
                    println("Блюдо $id закочилось")
                    flag = false;
                }
            } else {
                println("Некорректный выбор. Попробуйте снова.")
                break;
            }
        }
        if (!flag) {
            return;
        }
        val order = Order(curDishes, OrderStatus.processing, user)
        OrderDb.addOrder(order)
        val t = executorService.submit(
            Runnable {
                order.cooking()
            }
        )

    }
    fun payOrder(){
        println("Введите номер заказа для добавления блюда")
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        val order = OrderDb.getOrder(id)
    }

    override fun checkCurrentOrders(user: User) {
        println("Список ваших заказов:")
        var i = 1
        println("---------------------------\n")
        var flag: Boolean = true
        for (order in OrderDb.getListOfOrders()) {
            if (order.user == user) {
                print("$i. ")
                println(order)
                println("---------------------------")
                flag = false
            }
            i++
        }
        if (!flag) {
            println("У вас пока нет заказов")
        }
        val choice = readLine()?.toIntOrNull()
        while (true) {
            println("1. Вернуться в меню")
            println("2. Отменить заказ")
            println("3. Добавить блюдо в существующий заказ")
            println("4. Оплатить заказ")
            println("Выберите действие:")

            when (choice) {
                1 -> break
                2 -> removeOrder()
                3 -> addDishToOrder();
                4 -> payOrder();
            }
        }
    }

    override fun getListOfDishes() {
        val listOfDishes = DishDb.getListOfDishes()
        println("Список блюд:")

        var i = 1
        for (dish in listOfDishes) {
            print("$i. ")
            println(dish)
            i++
        }
    }

    fun addDishToOrder() {
        println("Введите номер заказа для добавления блюда")
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        val order = OrderDb.getOrder(id)
        if (id < order.dishes.size) {
            if (order.status == OrderStatus.processing) {
                getListOfDishes()
                println("Введите номер блюда для добавления блюда")
                val dishId = readLine()?.toIntOrNull()
                if (dishId == null) {
                    println("Некорректный ввод")
                    return
                }
                if (dishId >= DishDb.dishes.size) {
                    println("Блюда с таким номером не существует")
                    return

                }
                val dish = DishDb.dishes[dishId - 1]
                if(dish.number > 0) {
                    OrderDb.addDish(id, dish)
                    println("Блюдо добавлено")
                }
                else{
                    println("Блюдо закончилось")
                    return
                }
            } else {
                println("Заказ уже начали готовить, добавить блюда нельзя")
            }

        } else {
            println("Заказа с таким номером не существует")
        }
    }

    override fun addDish() {
        println("Введите название блюда")
        val name = readLine().toString()
        println("Введите количество для блюда")

        val number = readLine()?.toIntOrNull()
        if (number == null) {
            println("Некорректный ввод")
            return
        }

        println("Введите цену для блюда")
        val price = readLine()?.toDoubleOrNull()
        if (price == null) {
            println("Некорректный ввод")
            return
        }

        println("Введите сложность приготовления для блюда")
        val complexity = readLine()?.toIntOrNull()
        if (complexity == null) {
            println("Некорректный ввод")
            return
        }
        println("Блюдо успешно добавлено")
        DishDb.addDish(Dish(name, number, price, complexity))
    }

    override fun removeDish() {
        println("Введите номер блюда для удаления")
        getListOfDishes()
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.removeDish(id)
            println("Блюдо удалено")
        } else {
            println("Блюда с таким номером не существует")
        }
    }


    override fun changePrice() {
        println("Введите номер блюда для изменения цены")
        getListOfDishes()
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        println("Введите новую цену для блюда")
        val price = readLine()?.toDoubleOrNull()
        if (price == null) {
            println("Некорректный ввод")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.changePrice(id, price)
            println("Цена изменена")
        } else {
            println("Блюда с таким номером не существует")
        }
    }

    override fun changeComplexity() {
        println("Введите номер блюда для изменения сложности приготовления")
        getListOfDishes()
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        println("Введите новую сложность для блюда")
        val complexity = readLine()?.toIntOrNull()
        if (complexity == null) {
            println("Некорректный ввод")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.changeComplexity(id, complexity)
            println("Сложность изменена")
        } else {
            println("Блюда с таким номером не существует")
        }
    }

    fun removeOrder() {
        println("Введите номер заказа для удаления")
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        val order = OrderDb.getOrder(id)
        if (id <= order.dishes.size) {
            if (order.status == OrderStatus.processing || order.status == OrderStatus.preparing) {
                OrderDb.removeOrder(id)
                println("Заказ удален")
            } else {
                println("Заказ уже готов, отменить нельзя")
                return
            }
        } else {
            println("Заказа с таким номером не существует")
        }
    }

    override fun changeNumber() {
        println("Введите номер блюда для изменения количества")
        getListOfDishes()
        val id = readLine()?.toIntOrNull()
        if (id == null) {
            println("Некорректный ввод")
            return
        }
        println("Введите новое количество для блюда")
        val number = readLine()?.toIntOrNull()
        if (number == null) {
            println("Некорректный ввод")
            return
        }
        if (id <= DishDb.getListOfDishes().size) {
            DishDb.changeNumber(id, number)
            println("Количество изменено")
        } else {
            println("Блюда с таким номером не существует")
        }
    }
}