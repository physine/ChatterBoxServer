package physine.routing.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import org.koin.ktor.ext.inject
import physine.dtos.ChangePasswordRequest
import physine.dtos.CreateUserRequest
import physine.dtos.InfoDTO
import physine.dtos.LoginRequest
import physine.routing.json
import physine.services.UserService
import physine.utils.toDTO
import java.util.*

fun Application.configureUserRoutes() {
    val userService: UserService by inject()

    routing {

        get("/debug/user"){
            val infoDTO = call.receive<InfoDTO>()
            call.respondText(userService.getUserInfo(infoDTO.username).toString())
        }

        // create user
        post("/user") {
            println("[i] POST Request /user")
            val createUserRequest = call.receive<CreateUserRequest>()
            val createUserDTO = createUserRequest.toDTO()
            val response = userService.create(createUserDTO)
            call.respondText(json.encodeToString(response), ContentType.Application.Json)
        }

        // login
        post("/user/login") {
            println("[i] POST Request /user/login")
            val loginRequest = call.receive<LoginRequest>()
            val loginDTO = loginRequest.toDTO()
            val response = userService.login(loginDTO)
            call.respondText(json.encodeToString(response), ContentType.Application.Json)
        }

        authenticate("auth-jwt") {

            // change password
            put("/user") {
                println("[i] PUT Request /user")
                val principal = call.principal<JWTPrincipal>()
                if (principal == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    return@put
                }
                val changePasswordRequest = call.receive<ChangePasswordRequest>()
                val changePasswordDTO = changePasswordRequest.toDTO(principal)
                userService.changePassword(changePasswordDTO)
                call.respondText("Received POST: access granted", ContentType.Text.Plain)
            }

            // delete user
            delete("/user") {
                println("[i] DELETE Request /user")
                val principal = call.principal<JWTPrincipal>()
                if (principal == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    return@delete
                }
                val uuid = UUID.fromString(principal.payload.getClaim("uuid").asString())
                userService.delete(uuid)
                call.respondText("User deleted", ContentType.Text.Plain)
            }
        }
    }
}