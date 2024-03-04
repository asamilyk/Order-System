package Order

import AuthorizationSystem.User
import Dish.Dish

class Order(dishes: MutableList<Dish>, status: OrderStatus, user: User) {
    var Dishes: MutableList<Dish> = dishes
    var Status: OrderStatus = status
    var User: User = user
    override fun toString(): String {
        return "Статус: $Status, блюда:\n ${Dishes.forEach { it.toString() }}"
    }

}