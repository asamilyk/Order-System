package Order

import AuthorizationSystem.User
import Dish.Dish

class Order(dishes: MutableList<Dish>, status: OrderStatus, user: User) {
    var Dishes: MutableList<Dish> = dishes
    var Status: OrderStatus = status
    var User: User = user
    fun cooking(){
        Thread.sleep((1000*Dishes.size).toLong());
        Status = OrderStatus.preparing
        Thread.sleep((1000*Dishes.size).toLong());
        Status = OrderStatus.ready
    }
    override fun toString(): String {
        return "Статус: $Status, блюда:\n ${Dishes.forEach { it.toString() }}"
    }

}