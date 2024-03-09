package Order

import Dish.Dish
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class OrderDataBase {
    private var orders = mutableListOf<Order>()
    fun getListOfOrders(): List<Order> {
        return orders;
    }

    fun addOrder(order: Order) {
        orders.add(order)
    }

    fun removeOrder(id: Int) {
        orders.removeAt(id-1)
    }
    fun getOrder(id:Int):Order{
        return orders[id-1]
    }
    fun addDish(orderId: Int, dish: Dish){
        orders[orderId-1].Dishes.add(dish)
    }
    // Функция для сохранения данных о заказах в JSON файлы
    fun saveOrdersData(ordersFilePath: String) {
        val ordersJson = Json.encodeToString(orders)
        File(ordersFilePath).writeText(ordersJson)
        println("Данные о блзаказах сохранены в файл: $ordersFilePath")
    }

    // Функция для загрузки данных о заказах из JSON файлов
    fun loadOrdersData(ordersFilePath: String) {
        orders = try {
            val dishJson = File(ordersFilePath).readText()
            Json.decodeFromString<MutableList<Order>>(dishJson)
        } catch (e:Exception) {
            mutableListOf<Order>()
        }
    }



}