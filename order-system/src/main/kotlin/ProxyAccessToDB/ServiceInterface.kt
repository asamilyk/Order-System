package ProxyAccessToDB

import Dish

interface ServiceInterface {
    fun getListOfDishes()
    fun chooseDish()
    fun addDish()
    fun removeDish()
    fun changePrice()
    fun changeComplexity()
    fun changeNumber()
}