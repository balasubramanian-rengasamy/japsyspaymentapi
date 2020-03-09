package prime.utils

import org.jasypt.util.password.StrongPasswordEncryptor
import org.passay.*
import java.util.*

import java.util.concurrent.ThreadLocalRandom
import java.security.SecureRandom


val minPasswordLength = 8
val maxPasswordLength = 16

var specialCharacterRule = CharacterRule(object: CharacterData {
    override fun getErrorCode(): String {
        return "specialCharacterRule"
    }

    override fun getCharacters(): String {
        return "!@#$%^&*()"
    }
})

val passwordValidator = PasswordValidator(
        LengthRule(minPasswordLength, maxPasswordLength),
        CharacterRule(EnglishCharacterData.UpperCase, 1),
        CharacterRule(EnglishCharacterData.LowerCase, 1),
        CharacterRule(EnglishCharacterData.Digit, 1),
        specialCharacterRule,
        WhitespaceRule()
)

fun generateSafeToken(n:Int = 40): String {
    val milliSeconds = System.currentTimeMillis().toString()
    val token = Base64.getEncoder().withoutPadding().encodeToString(SecureRandom.getSeed(n))
    return "$token$milliSeconds"
}

object SecurityUtils {

    private val passwordEncryptor = StrongPasswordEncryptor()

    fun randomPassword():String {
        val passwordLength = ThreadLocalRandom.current().nextInt(minPasswordLength, maxPasswordLength)
        return PasswordGenerator().generatePassword(
                passwordLength,
                CharacterRule(EnglishCharacterData.UpperCase, 1),
                CharacterRule(EnglishCharacterData.LowerCase, 1),
                CharacterRule(EnglishCharacterData.Digit, 1),
                specialCharacterRule
        )
    }

    fun getEncryptedPassword(password: String): String = passwordEncryptor.encryptPassword(password)

    fun checkPassword(inputPassword: String, encryptedPassword: String): Boolean {
        return passwordEncryptor.checkPassword(inputPassword, encryptedPassword)
    }

    fun validatePassword(password:String):Boolean {
        val validate = passwordValidator.validate(PasswordData(password))

        if(!validate.isValid) {
            throw  Exception(validate.details!![0].errorCode)
        }

        return validate.isValid
    }
}