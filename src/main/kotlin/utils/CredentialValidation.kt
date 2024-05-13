package physine.utils

import physine.dtos.UserDTO

object CredentialValidation {

    private const val MAXIMUM_USERNAME_LENGTH = 16
    private const val MINIMUM_PASSWORD_LENGTH = 3

    fun verifyUserCredentialFormats(user: UserDTO): Boolean {
        return true
    }

    fun checkUsernameAvailability(username: String): Boolean {
        // todo make call to user repo
        return true
    }

    fun validate(userDTO: UserDTO): Boolean {
        return validateUsername(userDTO.username) && validatePassword(userDTO.password)
    }

    private fun validateUsername(username: String): Boolean {
        return username.isNotBlank() && username.length < MAXIMUM_USERNAME_LENGTH
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > MINIMUM_PASSWORD_LENGTH
    }
}