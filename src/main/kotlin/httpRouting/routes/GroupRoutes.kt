package physine.httpRouting.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import physine.dtos.CreateGroupRequest
import physine.services.GroupService

fun Application.configureGroupRoutes() {
    val groupService: GroupService by inject()

    routing {
        authenticate("auth-jwt") {

            // create group (group name, Creator id)
            post("/group/create") {
                val principal = call.principal<JWTPrincipal>()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                val createGroupRequest = call.receive<CreateGroupRequest>()
                val createGroupDTO = createGroupRequest.toDTO(principal)
                groupService.createGroup(createGroupDTO)
            //                val createGroupDTO = createGroupRequest("").toDTO("", principal)

//                groupService.createGroup()
//                call.respond(HttpStatusCode.Created, group)
            }

            post("/group/join") {
                val (groupId, username) = call.receive<Pair<String, String>>()
//                val group = groups.find { it.id == groupId }
//                group?.members?.add(username)
//                call.respond(HttpStatusCode.OK, group)
            }

            post("/group/leave") {
                val (groupId, username) = call.receive<Pair<String, String>>()
//                val group = groups.find { it.id == groupId }
//                group?.members?.remove(username)
//                call.respond(HttpStatusCode.OK, group)
            }
        }
    }
}