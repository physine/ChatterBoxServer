package physine.utils

import physine.dtos.CreateUserDTO

object CredentialValidation {

    private const val MAXIMUM_USERNAME_LENGTH = 16
    private const val MINIMUM_PASSWORD_LENGTH = 3

    fun verifyUserCredentialFormats(username: String, password: String): Boolean {
        return true
    }

    fun isUsernameAvailable(createUserDTO: CreateUserDTO): Boolean {
        // todo make call to user repo
        return true
    }

    fun validate(username: String, password: String): Boolean {
        return validateUsername(username) &&
                validatePassword(password)
    }

    private fun validateUsername(username: String): Boolean {
        return username.isNotBlank() && username.length < MAXIMUM_USERNAME_LENGTH
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > MINIMUM_PASSWORD_LENGTH
    }
}