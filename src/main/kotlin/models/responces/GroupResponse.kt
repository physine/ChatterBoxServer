package physine.models.responces

import kotlinx.serialization.Serializable

@Serializable
data class GroupResponse(
    val success: Boolean = true,
    val statusCode: Int = 200,
    val message: String = ""
)