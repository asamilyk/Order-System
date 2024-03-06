package Dish

class DishDataBaseService() {

    private var dishes = mutableListOf<Dish>()
    fun getListOfDishes(): List<Dish> {
        return dishes;
    }

    fun addDish(dish: Dish) {
        dishes.add(dish)
    }

    fun removeDish(id: Int) {
        dishes.removeAt(id-1)
    }

    fun changeNumber(id: Int, number: Int) {
        dishes[id-1].Number = number
    }

    fun changePrice(id: Int, price: Float) {
        dishes[id-1].Price = price
    }

    fun changeComplexity(id: Int, complexity: Int) {
        dishes[id-1].Complexity = complexity
    }

}