package physine.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import physine.dtos.UserDTO
import physine.services.UserService

fun Application.configureUserRoutes() {
    val userService: UserService by inject()

    routing {
        get("/user") {
            println("\nHTTP - GET - /user\n")
            userService.getUser()
//            call.respondText("GET Request - Endpoint: \"$ROOT\"", ContentType.Text.Plain)
        }

        // create user
        post("/user") {
            println("HTTP - POST - /user")
            val user = call.receive<UserDTO>()
            println("user: ${user.username} ${user.password}")
            userService.createUser(user)

            call.respondText("ok")
        }

        // update user (only the password, not the username)
        put("/user/{id}") {  // Update might need to be more specific like "/user/update/{id}"
            userService.updateUser()  // Assuming updateUser method exists and needs request data
            val updateData = call.receiveText()
            call.respondText("Received POST: $updateData", ContentType.Text.Plain)
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