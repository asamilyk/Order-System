package Dish

import kotlinx.serialization.Serializable

@Serializable
class Dish(var name: String, var number: Int, var price: Double, var complexity: Int) {
    override fun toString(): String {
        return "Название: $name, кол-во: $number, цена: $price, сложность: $complexity"
    }

}