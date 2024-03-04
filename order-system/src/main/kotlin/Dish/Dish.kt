package Dish

class Dish(name: String, number: Int, price: Float, complexity: Int) {
    var Name: String = name
    var Number: Int = number
    var Price: Float = price
    var Complexity: Int = complexity
    override fun toString(): String {
        return "Название: $Name, кол-во: $Number, цена: $Price, сложность: $Complexity"
    }

}