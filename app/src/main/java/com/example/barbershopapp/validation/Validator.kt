import android.util.Patterns
import java.util.Date

object Validator {
    fun validateName(name: String): ValidationResult {
        return ValidationResult(
            (!name.isNullOrEmpty() && name.length >= 2 && name.all { it.isLetter() }),
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty() && isEmailValid(email))
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 8 && isPasswordValid(password))
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidationResult {
        return ValidationResult(
             statusValue
        )
    }


    fun validatePhone(phone: String): ValidationResult {
        val phoneStr = phone.toString()
        return ValidationResult(
         isNumber(phoneStr)

        )
    }



    fun validateDate(date: Date): ValidationResult {
        return ValidationResult(
            isDateValid(date)
        )
    }


    fun isNumber(value: String): Boolean {
        return Patterns.PHONE.matcher(value).matches() }
    }

    fun isDateValid(date: Date): Boolean {
        val currentDate = Date()
        return !date.after(currentDate)
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.any { it.isDigit() } && password.any { it.isLetter() }
    }

data class ValidationResult(
    val status: Boolean = false
)