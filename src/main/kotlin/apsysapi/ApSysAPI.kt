package apsysapi


import japsapi.*
import org.apache.commons.lang3.exception.ExceptionUtils
import prime.utils.log
import java.io.FileInputStream
import java.util.*

class ApSysAPI {

    private val jas = JApsApi()
    private var connection : JApsConnect? = null
    private var session : JApsSession? = null
    private var prop = Properties()

    init {
        log.info("initializing environment variables")
        val apsysIniFile = System.getProperty("apsysIniFile")
        val testFile = System.getProperty("testFile")
        init(apsysIniFile, testFile)
    }

    private fun init(apsysIniFile: String, testIni: String) {
        connection = JApsConnect()

        if ( connection!!.connect(apsysIniFile) != JApsError.APS_SUCCESS) {
            log.info("Unable to connect to APSYS Server")
            throw Exception("Unable to connect to APSYS Server")
        }

        try {
            connection!!.stateObject.registerCallBack(connection)
        } catch (e: JApsException) {
            log.error(ExceptionUtils.getStackTrace(e))
        }

        log.info("Status object on connection")

        val status  = """
            Available : ${connection!!.stateObject.available}
            Write :  ${connection!!.stateObject.write}
            Accounting Date :  ${connection!!.stateObject.dtcptl}
            Nusesi :  ${connection!!.stateObject.nusesi}
            State:  ${connection!!.stateObject.envState}
        """.trimIndent()

        log.info(status)

        try {
            prop.load(FileInputStream(testIni))
        } catch (ef: Exception) {
            log.error("Error loading configuration file: $testIni", testIni)
        }
    }

    private fun release() { connection!!.disconnect() }

    private fun getProperty(token: String): String {
        var property = token.trim { it <= ' ' }
        if (property.endsWith(":"))
            property = property.substring(0, property.length - 1)
        return prop.getProperty(property, "").trim()
    }

    fun Session():JApsSession {
        if(  connection!!.connect() != 1) {
            throw java.lang.Exception("Unable to connect apsys server")
        }
        try {
            val username = getProperty("Login.User")
            val password = getProperty("Login.Pass")
            session = connection!!.login(username, password)
            return session!!
        }
        catch (ex:Exception) {
            throw Exception("Service not available.")
        }
    }

}
