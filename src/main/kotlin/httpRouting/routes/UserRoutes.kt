package physine.routing.routes

import ch.qos.logback.classic.Logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory
import physine.dtos.ChangePasswordRequest
import physine.dtos.CreateUserRequest
import physine.dtos.InfoDTO
import physine.dtos.LoginRequest
import physine.routing.json
import physine.services.UserService
import java.util.*

fun Application.configureUserRoutes() {
    val userService: UserService by inject()

    val log = LoggerFactory.getLogger("app") as Logger

    routing {

        get("/health-check") {
            return@get call.respondText("OK", ContentType.Text.Plain)
        }

        get("/debug/user") {
            val infoDTO = call.receive<InfoDTO>()
            return@get call.respondText(userService.getUserInfo(infoDTO.username).toString())
        }

        // create user - ok
        post("/user") {
            println("[i] POST Request /user")
            val createUserRequest = call.receive<CreateUserRequest>()
            val createUserDTO = createUserRequest.toDTO()
            val response = userService.create(createUserDTO)
            return@post call.respondText(json.encodeToString(response), ContentType.Application.Json)
        }

        // login - ok
        post("/user/login") {
            println("[i] POST Request /user/login")
            val loginRequest = call.receive<LoginRequest>()
            val loginDTO = loginRequest.toDTO()
            val response = userService.login(loginDTO)
            return@post call.respondText(json.encodeToString(response), ContentType.Application.Json)
        }

        authenticate("auth-jwt") {

            // change password - ok
            put("/user") {
                log.info("[i] PUT /user")
                val principal = call.principal<JWTPrincipal>()
                    ?: return@put call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                val changePasswordRequest = call.receive<ChangePasswordRequest>()
                val changePasswordDTO = changePasswordRequest.toDTO(principal)
                val response = userService.changePassword(changePasswordDTO)
                call.response.status(HttpStatusCode(response.statusCode, response.message))
                return@put call.respondText(json.encodeToString(response), ContentType.Application.Json)
            }

            // delete user - ok
            delete("/user") {
                log.info("[i] DELETE Request /user")
                val principal = call.principal<JWTPrincipal>()
                    ?: return@delete call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                val uuid = UUID.fromString(principal.payload.getClaim("uuid").asString())
                userService.delete(uuid)
                call.respondText("User deleted", ContentType.Text.Plain)
            }
        }
    }
}