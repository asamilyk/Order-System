package Order

import AuthorizationSystem.User
import Dish.Dish

class Order(dishes: MutableList<Dish>, status: OrderStatus, user: User) {
    var Dishes: MutableList<Dish> = dishes
    var Status: OrderStatus = status
    var User: User = user
    fun cooking(){
        Thread.sleep((10000*Dishes.size).toLong());
        Status = OrderStatus.preparing
        Thread.sleep((10000*Dishes.size).toLong());
        Status = OrderStatus.ready
    }
    override fun toString(): String {
        var str = "Статус: $Status, блюда:\n";
        var i = 1
        for(dish in Dishes){
            str += "$i. ${dish}+\n"
            i++
        }
        return str
    }

}