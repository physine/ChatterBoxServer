package physine.models.responces

data class GroupResponse(
    val success: Boolean = true,
    val statusCode: Int = 200,
    val message: String = ""
)