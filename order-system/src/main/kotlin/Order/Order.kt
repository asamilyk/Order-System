package Order

import AuthorizationSystem.User
import Dish.Dish


class Order(var dishes: MutableList<Dish>, var status: OrderStatus, var user: User) {
    var cost: Double = dishes.sumOf { it.price }
    fun cooking(orderDb: OrderDataBase){
        var complexity = 0;
        val time = 1000;
        for(dish in dishes){
            complexity += dish.complexity
        }
        if (orderDb.getListOfUserOrders(user).size < 5){
            Thread.sleep(time.toLong())
        }
        Thread.sleep((time*complexity).toLong());
        status = OrderStatus.preparing
        Thread.sleep((time*complexity).toLong());
        status = OrderStatus.ready
    }

    override fun toString(): String {
        var str = "Статус: $status, стоимость: $cost, блюда:\n";
        var i = 1
        for(dish in dishes){
            str += "$i. ${dish}\n"
            i++
        }
        return str
    }

}