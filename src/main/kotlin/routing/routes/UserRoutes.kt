package physine.routing.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import org.koin.ktor.ext.inject
import physine.dtos.UserDTO
import physine.routing.json
import physine.services.UserService

fun Application.configureUserRoutes() {
    val userService: UserService by inject()

    routing {
        post("/user") {
            println("HTTP - POST - /user")
            val userDTO = call.receive<UserDTO>()
            val response = userService.createUser(userDTO)
            call.respondText(json.encodeToString(response), ContentType.Application.Json)
        }

        authenticate("auth-jwt") {
            // update user (only the password, not the username)
            put("/user") {  // Update might need to be more specific like "/user/update/{id}"
                println("HTTP - PUT - /user")
//                userService.updateUser()  // Assuming updateUser method exists and needs request data
//                val updateData = call.receiveText()
                call.respondText("Received POST: access granted", ContentType.Text.Plain)
            }

            // delete user
            delete("/user/{id}") {
                val userId = call.parameters["id"] ?:
                        return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")
    //                userService.deleteUser(userId)
                call.respondText("User $userId deleted", ContentType.Text.Plain)
            }
        }
    }
}