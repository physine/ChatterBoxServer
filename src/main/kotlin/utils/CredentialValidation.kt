package physine.utils

object CredentialValidation {

    private const val MAXIMUM_USERNAME_LENGTH = 16
    private const val MINIMUM_PASSWORD_LENGTH = 1

    fun verifyUserCredentialFormats(username: String, password: String): Boolean {
        return true
    }

    fun validateCreds(username: String, password: String): Boolean {
        return validateUsername(username) && validatePassword(password)
    }

    fun validateUsername(username: String): Boolean {
        return username.isNotBlank() && (username.length < MAXIMUM_USERNAME_LENGTH)
    }

    fun validatePassword(password: String): Boolean {
        return password.length > MINIMUM_PASSWORD_LENGTH
    }
}