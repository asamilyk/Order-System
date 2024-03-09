package Order

import Dish.Dish

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



}