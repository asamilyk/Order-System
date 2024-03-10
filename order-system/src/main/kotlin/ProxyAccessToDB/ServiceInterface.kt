package ProxyAccessToDB

import AuthorizationSystem.User
import java.util.concurrent.ExecutorService

interface ServiceInterface {
    fun getListOfDishes()
    fun addDish()
    fun removeDish()
    fun changePrice()
    fun changeComplexity()
    fun changeNumber()
    fun createOrder(user: User, executorService: ExecutorService)
    fun checkCurrentOrders(user: User)
    fun getStatistics()
}