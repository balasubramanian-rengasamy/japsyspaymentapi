package prime.utils

fun fail(message:String):Nothing {
    throw IllegalArgumentException(message)
}

fun fail(condition:Boolean, message:String):Unit {
    if(condition) throw IllegalArgumentException(message)
}