package prime.utils

import org.apache.commons.lang3.StringUtils

fun substring(str:String?, start:Int, end:Int):String {
    val str1 =StringUtils.defaultIfEmpty(str, "")
    return StringUtils.substring(str1, start, end)
}

fun String.substringBetween(open: String?, close: String?):String? {
    return StringUtils.substringBetween(this, open, close)
}

