package ProxyAccessToDB

import Dish

interface ServiceInterface {
    fun getListOfDishes():List<Dish>
    fun addDish(dish:Dish)
    fun removeDish(id:Int)
    fun changePrice(id:Int, price:Float)
    fun changeComplexity(id:Int, complexity:Int)
    fun changeNumber(id:Int, number:Int)
}