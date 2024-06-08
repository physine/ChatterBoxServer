package physine.models.responces

import kotlinx.serialization.Serializable

@Serializable
object UserResponses {

    fun userCreationSuccessful(token: String): UserResponse {
        return UserResponse(
            message = "Account Creation Successful. Logged In.",
            jwt = token
        )
    }

    fun userCreationNotSuccessful(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Could Not Create Account."
        )
    }

    fun logInSuccessful(token: String): UserResponse {
        return UserResponse(
            message = "Login Successful.",
            jwt = token
        )
    }

    fun logInNotSuccessful(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Login Not Successful. Username and/or password is wrong."
        )
    }

    fun usernameUnavailable(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Username Not Available."
        )
    }

    fun passwordCouldNotBeChanged(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Password Could Not Be Changed."
        )
    }

    fun passwordChanged(): UserResponse {
        return UserResponse(
            message = "Password Changed."
        )
    }

    fun deleteNotSuccessful(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Delete Not Successful."
        )
    }

    fun deleteSuccessful(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Delete Successful."
        )
    }

    fun invalidPassword(): UserResponse {
        return UserResponse(
            success = false,
            statusCode = 400,
            message = "Invalid Password. Too Short."
        )
    }
}