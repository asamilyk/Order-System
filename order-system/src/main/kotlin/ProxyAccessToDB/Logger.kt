package ProxyAccessToDB

class Logger {
    fun writeMessageGet() {
        println("user try to get list of dishes")
    }

    fun writeMessageAdd(id:Int, name : String, success : Boolean ) {
        println("user try to add dish - ${name}. success: ${success} ")
    }

    fun writeMessageRemove(id : Int, success : Boolean) {
        println("user try to remove dish - ${id}. success: ${success} ")
    }

    fun writeMessageChange(id : Int, success : Boolean) {
        println("user try to change dish - ${id}. success: ${success} ")
    }
}