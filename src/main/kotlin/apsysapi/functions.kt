package apsysapi


import prime.utils.asString
import prime.utils.plusDays
import java.util.*


fun getEndValidatityDate(noOfDays:Int = 2):Int {
    val dtList = ArrayList<Date>()
    val holiday = listOf("Sat", "Sun")

    for ( i in 0..31) {
        val dt = Date().plusDays(i)

        if (!holiday.contains(dt.asString("EEE"))) {
            dtList.add(dt)
        }
    }
    return dtList[noOfDays].asString("yyyyMMdd").toInt()
}
