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
            message = "Joined group.",
        )
    }
    fun userRemovedFromGroup(): GroupResponse {
        return GroupResponse(
            message = "Left group."
        )
    }
    fun groupNotFoundResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "Group not found."
        )
    }
    fun invalidGroupOperationResponse(): GroupResponse {
        return GroupResponse(
            success = false,
            statusCode = 400,
            message = "Invalid group operations. You need to be the creator a group to delete it."
        )
    }
    fun operationSuccessfulResponse(): GroupResponse {
        return GroupResponse(
            success = true,
            statusCode = 200,
            message = "Operation successful."
        )
    }
}