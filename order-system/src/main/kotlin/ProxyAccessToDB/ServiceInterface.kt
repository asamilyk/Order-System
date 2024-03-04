package ProxyAccessToDB

import AuthorizationSystem.User

interface ServiceInterface {
    fun getListOfDishes()
    fun addDish()
    fun removeDish()
    fun changePrice()
    fun changeComplexity()
    fun changeNumber()
    fun createOrder(user: User)
    fun checkCurrentOrders(user: User)
}