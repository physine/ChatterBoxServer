package physine.models.responces

import kotlinx.serialization.Serializable

@Serializable
object GroupResponses {

    fun groupNameNotValidResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "Invalid group name."
        )
    }
    fun groupNameNotAvailableResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "Group name not available."
        )
    }
    fun groupCreatedResponse(): GroupResponse {
        return GroupResponse(
            message = "Group created.",
        )
    }
    fun noSuchGroupResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "No such group."
        )
    }
    fun alreadyInGroupResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "User already in group."
        )
    }

    fun userNotFoundResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "User not found."
        )
    }
    fun userAddedToGroup(): GroupResponse {
        return GroupResponse(
            message = "User added to group.",
        )
    }
    fun userRemovedFromGroup(): GroupResponse {
        return GroupResponse(
            message = "User removed from group."
        )
    }
}