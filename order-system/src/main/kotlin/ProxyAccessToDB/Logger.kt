package ProxyAccessToDB

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
}