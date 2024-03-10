package InfoForAdmin

import Order.Order

class Statistics {
    var numberOfOrders: Int = 0
    var numberOfDishes = mutableListOf<Int>()
    var costOfDishes = mutableListOf<Double>()
    var everageNumberOfDishes = if (numberOfDishes.size == 0) 0 else numberOfDishes.sum() / numberOfDishes.size
    var everageCostOfDishes = if (costOfDishes.size == 0) 0 else costOfDishes.sum() / costOfDishes.size
    fun plusOrder(order: Order) {
        numberOfOrders++;
        numberOfDishes.add(order.dishes.size)
        costOfDishes.add(order.cost)
    }

}