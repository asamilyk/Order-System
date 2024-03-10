package InfoForAdmin

import Order.Order


class Feedback {
    val evaluations = mutableListOf<Int>()
    val messages = mutableListOf<Pair<Order, String>>()

    var everageEval = if (evaluations.size == 0) 0 else evaluations.sum()/evaluations.size

    fun addFeedback(evaluation :Int, message: String, order:Order){
        evaluations.add(evaluation)
        messages.add(order to message)
    }
}