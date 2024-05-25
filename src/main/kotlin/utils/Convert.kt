package physine.utils

import io.ktor.server.auth.jwt.*
import physine.dtos.*

//fun jwtPrincipalToUserDTO(principal: JWTPrincipal): UserDTO {
//    return UserDTO(
//        username = principal.payload.getClaim("username").asString(),
//        uuid = UUID.fromString(principal.payload.getClaim("uuid").asString()),
//        password = principal.payload.getClaim("password").asString()
//    )
//}

fun CreateUserRequest.toDTO(): CreateUserDTO {
    return CreateUserDTO(
        username = this.username,
        password = this.password,
    )
}

fun LoginRequest.toDTO(): LoginDTO {
    return LoginDTO(
        username = this.username,
        password = this.password
    )
}

fun ChangePasswordRequest.toDTO(principal: JWTPrincipal): ChangePasswordDTO {
    return ChangePasswordDTO(
        username = principal.payload.getClaim("username").asString(),
        newPassword = this.newPassword
    )
}