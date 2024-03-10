package ProxyAccessToDB

// Класс, методы которого можно раскомментировать при необходимости получать логи
class Logger {
    fun writeMessageGet() {
        //println("user try to get list of dishes")
    }

    fun writeMessageAdd(success : Boolean ) {
        //println("user try to add dish - . success: ${success} ")
    }

    fun writeMessageRemove(success : Boolean) {
        //println("user try to remove dish. success: ${success} ")
    }

    fun writeMessageChange(success : Boolean) {
        //println("user try to change dish. success: ${success} ")
    }
    fun writeMessageGetStatistics(success : Boolean) {
        //println("user try to get statistics. success: ${success} ")
    }
}