package Dish

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class DishDataBase() {

    var dishes = mutableListOf<Dish>()
    fun getListOfDishes(): List<Dish> {
        return dishes
    }

    fun addDish(dish: Dish) {
        dishes.add(dish)
    }

    fun removeDish(id: Int) {
        dishes.removeAt(id-1)
    }

    fun changeNumber(id: Int, number: Int) {
        dishes[id-1].number = number
    }

    fun changePrice(id: Int, price: Float) {
        dishes[id-1].price = price
    }

    fun changeComplexity(id: Int, complexity: Int) {
        dishes[id-1].complexity = complexity
    }

    // Функция для сохранения данных о блюдах в JSON файлы
    fun saveDishData(dishFilePath: String) {
        val dishJson = Json.encodeToString(dishes)
        File(dishFilePath).writeText(dishJson)
        println("Данные о блюдах сохранены в файл: $dishFilePath")
    }

    // Функция для загрузки данных о блюдах из JSON файлов
    fun loadDishData(dishFilePath: String) {
        dishes = try {
            val dishJson = File(dishFilePath).readText()
            Json.decodeFromString<MutableList<Dish>>(dishJson)
        } catch (e:Exception) {
            mutableListOf<Dish>()
        }
    }

}