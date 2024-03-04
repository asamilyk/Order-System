package ProxyAccessToDB

import AuthorizationSystem.User

class Accessor(private var realService : DishDataBase, var role : Role) : ServiceInterface {
    var logger = Logger()

    fun checkAccess(role : Role) : Boolean {
        if (role == Role.Admin) {
            return true
        }
        return false
    }

    override fun getListOfDishes() {
        logger.writeMessageGet()
        realService.getListOfDishes()
    }

    override fun addDish() {
        if (checkAccess(role)) {
            logger.writeMessageAdd(true)
            realService.addDish()
        } else {
            logger.writeMessageAdd(false)
        }
    }

    override fun removeDish() {
        if (checkAccess(role)) {
            logger.writeMessageRemove(true)
            realService.removeDish()
        } else {
            logger.writeMessageRemove( false)
        }
    }

    override fun changePrice() {
        if (checkAccess(role)) {
            logger.writeMessageChange(true)
            realService.changePrice();
        } else {
            logger.writeMessageChange(false)
        }
    }

    override fun changeComplexity() {
        if (checkAccess(role)) {
            logger.writeMessageChange(true)
            realService.changeComplexity()
        } else {
            logger.writeMessageChange(false)
        }
    }

    override fun changeNumber() {
        if (checkAccess(role)) {
            logger.writeMessageChange(true)
            realService.changeNumber()
        } else {
            logger.writeMessageChange(false)
        }
    }
    override fun createOrder(user: User){
        realService.createOrder(user)
    }
    override fun checkCurrentOrders(user: User){
        realService.checkCurrentOrders(user)
    }
}