package ProxyAccessToDB

import Dish

class DishDataBase: ServiceInterface {
    var dishes = mutableListOf<Dish>()

    override fun getListOfDishes(): List<Dish> {
        return dishes;
    }

    override fun addDish(dish:Dish) {
        dishes.add(dish)
    }

    override fun removeDish(id: Int) {
        if (id < dishes.size) {
            dishes.removeAt(id-1)
        } else {
            throw Exception("no element with this id")
        }
    }

    override fun changePrice(id:Int, price:Float){
        if (id < dishes.size) {
            dishes[id-1].Price = price
        } else {
            throw Exception("no dish with this id")
        }
    }
    override fun changeComplexity(id:Int, complexity:Int){
        if (id < dishes.size) {
            dishes[id-1].Complexity = complexity;
        } else {
            throw Exception("no dish with this id")
        }
    }
    override fun changeNumber(id:Int, number:Int){
        if (id < dishes.size) {
            dishes[id-1].Number = number;
        } else {
            throw Exception("no dish with this id")
        }
    }


}