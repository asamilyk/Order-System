package Order

import AuthorizationSystem.User
import Dish.Dish


class Order(var dishes: MutableList<Dish>, var status: OrderStatus, var user: User) {
    var cost: Double = dishes.sumOf { it.price }
    fun cooking(){
        var complexity = 0;
        for(dish in dishes){
            complexity += dish.complexity
        }
        Thread.sleep((10000*complexity).toLong());
        status = OrderStatus.preparing
        Thread.sleep((10000*complexity).toLong());
        status = OrderStatus.ready
    }
    override fun toString(): String {
        var str = "Статус: $status, стоимость: $cost, блюда:\n";
        var i = 1
        for(dish in dishes){
            str += "$i. ${dish}+\n"
            i++
        }
        return str
    }

}