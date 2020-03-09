package prime.utils

import com.mitchellbosecke.pebble.PebbleEngine
import com.mitchellbosecke.pebble.loader.StringLoader
import java.io.StringWriter

data class StringTemplate(private val inputString:String) {

    private val context = HashMap<String, Any>()

    fun put(key:String, value:Any):StringTemplate {
        context[key] = value
        return this
    }

    fun evaluate():String {
        val engine = PebbleEngine.Builder().loader(StringLoader()).build()
        val writer = StringWriter()
        engine.getTemplate(inputString).evaluate(writer, context)
        return writer.toString()
    }
}