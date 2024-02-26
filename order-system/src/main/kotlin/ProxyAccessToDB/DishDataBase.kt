package ProxyAccessToDB

import DataBaseService
import Dish
import java.util.*

class DishDataBase: ServiceInterface {
    var db = DataBaseService()
    var dishes = mutableListOf<Dish>()
    val scanner = Scanner(System.`in`)

    override fun getListOfDishes() {
        val listOfDishes = db.getListOdDishes()
        println("Список блюд:")
        if (listOfDishes != null) {
            for(dish in listOfDishes){
                println(dish)
            }
        }

    }

    override fun addDish() {
        println("Введите название блюда")
        val name = scanner.next()
        println("Введите количество для блюда")
        val number = scanner.nextInt()
        println("Введите цену для блюда")
        val price = scanner.nextFloat()
        println("Введите сложность для блюда")
        val complexity = scanner.nextInt()
        db.addDish(Dish(name, number, price, complexity))
    }

    override fun removeDish() {
        println("Введите номер блюда для удаления")
        getListOfDishes()
        val id = scanner.nextInt()
        if (id < dishes.size) {
            db.removeDish(id)
        } else {
            throw Exception("no element with this id")
        }
    }

    override fun changePrice(){
        println("Введите номер блюда для изменения цены")
        getListOfDishes()
        val id = scanner.nextInt()
        println("Введите новую цену для блюда")
        getListOfDishes()
        val price = scanner.nextFloat()
        if (id < dishes.size) {
            db.changePrice(id, price)
        } else {
            throw Exception("no dish with this id")
        }
    }
    override fun changeComplexity(){
        println("Введите номер блюда для изменения сложности приготовления")
        getListOfDishes()
        val id = scanner.nextInt()
        println("Введите новую сложность для блюда")
        getListOfDishes()
        val complexity = scanner.nextInt()
        if (id < dishes.size) {
            db.changeComplexity(id, complexity)
        } else {
            throw Exception("no dish with this id")
        }
    }
    override fun changeNumber(){
        println("Введите номер блюда для изменения количества")
        getListOfDishes()
        val id = scanner.nextInt()
        println("Введите новое количество для блюда")
        getListOfDishes()
        val number = scanner.nextInt()
        if (id < dishes.size) {
            db.changeNumber(id, number)
        } else {
            throw Exception("no dish with this id")
        }
    }
}