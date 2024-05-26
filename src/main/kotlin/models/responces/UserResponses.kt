package physine.models.responces

import kotlinx.serialization.Serializable

@Serializable
object UserResponses {

    fun userCreationSuccessful(token: String): Response {
        return Response(
            message = "Account Creation Successful. Logged In.",
            jwt = token)
    }

    fun userCreationNotSuccessful(): Response {
        return Response(
            success = false,
            message = "Could Note Create Account.")
    }

    fun logInSuccessful(token: String): Response {
        return Response(
            message = "Login Successful.",
            jwt = token)
    }

    fun logInNotSuccessful(): Response {
        return Response(
            success = false,
            message = "Login Not Successful. Username and/or password is wrong.")
    }

    fun usernameUnavailable(): Response {
        return Response(
            success = false,
            message = "Username Not Available.")
    }

    fun passwordCouldNotBeChanged(): Response {
        return Response(
            success = false,
            message = "Password Could Not Be Changed.")
    }

    fun passwordChanged(): Response {
        return Response(message = "Password Changed.")
    }

    fun deleteNotSuccessful(): Response {
        return Response(
            success = false,
            message = "Delete Not Successful.")
    }

    fun deleteSuccessful(): Response {
        return Response(message = "Delete Successful.")
    }
}