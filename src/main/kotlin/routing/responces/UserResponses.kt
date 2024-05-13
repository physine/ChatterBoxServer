package physine.routing.responces

import kotlinx.serialization.Serializable

@Serializable
object UserResponses {

    fun userCreationSuccessful(token: String): Response {
        return Response(
            message = "Account Creation Successful.",
            jwt = token)
    }

    fun logInSuccessful(): Response {
        return Response(message = "Login Successful.")
    }

    fun logInNotSuccessful(): Response {
        return Response(message = "Login Not Successful. Username or password is wrong.")
    }
}