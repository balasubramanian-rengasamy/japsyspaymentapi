package prime.utils

import org.joda.time.DateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format:String): Date {
    return SimpleDateFormat(format).parse(this)
}

fun String.toDate(): Date {
    return this.toDate("dd-MMM-yyyy")
}

fun Date.asString(format:String):String = asString(SimpleDateFormat(format))
fun Date.asString(format: DateFormat):String = format.format(this)

fun Date.toCalendar():Calendar  {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Date.plusMinutes(minutes:Int): Date {
    val cal = this.toCalendar()
    cal.add(Calendar.MINUTE, minutes)
    return cal.time
}

fun Date.plusDays(days:Int): Date = this.toDateTime().plusDays(days).toDate()
fun Date.minusDays(days:Int): Date = this.toDateTime().plusDays(days*-1).toDate()

fun Date.toDateTime():DateTime = DateTime(this)