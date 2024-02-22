package ProxyAccessToDB

import Dish

class Accessor(private var realService : DishDataBase, var role : Role) : ServiceInterface {
    var logger = Logger()

    fun checkAccess(role : Role) : Boolean {
        if (role == Role.Admin) {
            return true
        }
        return false
    }

    override fun getListOfDishes():List<Dish> {
        logger.writeMessageGet()
        return realService.getListOfDishes()
    }

    override fun addDish(dish:Dish) {
        if (checkAccess(role)) {
            logger.writeMessageAdd(realService.dishes.size, dish.Name, true)
            realService.addDish(dish)
        } else {
            logger.writeMessageAdd(realService.dishes.size, dish.Name,false)
        }
    }

    override fun removeDish(id: Int) {
        if (checkAccess(role)) {
            logger.writeMessageRemove(id, true)
            realService.removeDish(id)
        } else {
            logger.writeMessageRemove(id, false)
        }
    }

    override fun changePrice(id: Int, price: Float) {
        if (checkAccess(role)) {
            logger.writeMessageChange(id, true)
            realService.dishes[id].Price = price;
        } else {
            logger.writeMessageChange(id, false)
        }
    }

    override fun changeComplexity(id: Int, complexity: Int) {
        if (checkAccess(role)) {
            logger.writeMessageChange(id, true)
            realService.dishes[id].Complexity = complexity
        } else {
            logger.writeMessageChange(id, false)
        }
    }

    override fun changeNumber(id: Int, number: Int) {
        if (checkAccess(role)) {
            logger.writeMessageChange(id, true)
            realService.dishes[id].Number = number
        } else {
            logger.writeMessageChange(id, false)
        }
    }
}