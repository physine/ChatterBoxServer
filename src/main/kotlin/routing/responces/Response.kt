package physine.routing.responces

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val success: Boolean = true,
    val statusCode: Int = 200,
    val message: String = "",
    val jwt: String = ""
)