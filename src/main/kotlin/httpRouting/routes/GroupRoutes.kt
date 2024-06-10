package physine.httpRouting.routes

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
import physine.dtos.CreateGroupRequest
import physine.dtos.JoinGroupRequest
import physine.dtos.LeaveGroupRequest
import physine.routing.json
import physine.services.GroupService

fun Application.configureGroupRoutes() {
    val groupService: GroupService by inject()
    val log = LoggerFactory.getLogger("app") as Logger

    routing {
        authenticate("auth-jwt") {

            // create group (group name, Creator id)
            post("/group/create") {
                val principal = call.principal<JWTPrincipal>()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                val createGroupRequest = call.receive<CreateGroupRequest>()
                val createGroupDTO = createGroupRequest.toDTO(principal)
                val response = groupService.createGroup(createGroupDTO)
                call.response.status(HttpStatusCode(response.statusCode, response.message))
                return@post call.respondText(json.encodeToString(response), ContentType.Application.Json)
            }

            // join group
            post("/group/join") {
                log.info("$this /group/join 1")
                val principal = call.principal<JWTPrincipal>()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                val joinGroupRequest = call.receive<JoinGroupRequest>()
                val joinGroupDTO = joinGroupRequest.toDTO(principal)
                val response = groupService.joinGroup(joinGroupDTO)
                call.response.status(HttpStatusCode(response.statusCode, response.message))
                return@post call.respondText(json.encodeToString(response), ContentType.Application.Json)
            }

            // leave group
            post("/group/leave") {
                log.info("$this /group/leave 1")
                val principal = call.principal<JWTPrincipal>()
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                val leaveGroupRequest = call.receive<LeaveGroupRequest>()
                val leaveGroupDTO = leaveGroupRequest.toDTO(principal)
                val response = groupService.leaveGroup(leaveGroupDTO)
                call.response.status(HttpStatusCode(response.statusCode, response.message))
                return@post call.respondText(json.encodeToString(response), ContentType.Application.Json)
            }
        }
    }
}