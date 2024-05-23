package physine.models.responces

import kotlinx.serialization.Serializable

@Serializable
object UserResponses {

    fun userCreationSuccessful(token: String): Response {
        return Response(
            message = "Account Creation Successful. Logged In.",
            jwt = token)
    }

    fun logInSuccessful(token: String): Response {
        return Response(
            message = "Login Successful.",
            jwt = token)
    }

    fun logInNotSuccessful(): Response {
        return Response(message = "Login Not Successful. Username and/or password is wrong.")
    }

    fun usernameUnavailable(): Response {
        return Response(message = "Username Not Available.")
    }
}