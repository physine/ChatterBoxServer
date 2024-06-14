
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import physine.chatterBoxApi.ChatterBox
import physine.models.responces.GroupResponse
import physine.models.responces.UserResponse
import java.net.http.HttpResponse
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupTest {

    private val url = "$baseUrl/group"

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            startTestEnvironmentConfig()
            runBlocking {
                startServer()
                waitForServer()
                // create user and extract jwt for group tests
                val response = doPostRequest("$baseUrl/user", credentials)
                var bodyJson = objectMapper.readValue(response.body().toString(), UserResponse::class.java)
                jwt = bodyJson.jwt
            }
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            endTestEnvironmentConfig()
        }

        @JvmStatic
        private suspend fun startServer() = coroutineScope {
            GlobalScope.launch {
                ChatterBox().run()
            }
        }
    }

    @BeforeEach
    fun beforeEach(){
        dropAllRows("groups")
    }

    @Test
    fun createGroup(){
        val response = createGroup("test_group")
        var bodyJsonStr = response.body().toString()
        var bodyJson = objectMapper.readValue(bodyJsonStr, GroupResponse::class.java)

        assertEquals("Group created.", bodyJson.message)
    }

    @Test
    fun joinGroupTest(){
        createGroup("test_group")
        val response = joinGroup(getGroupId())
        var bodyJsonStr = response.body().toString()
        var responseBody = objectMapper.readValue(bodyJsonStr, GroupResponse::class.java)

        assertEquals("Joined group.", responseBody.message)
    }

    @Test
    fun leaveGroupTest(){
        createGroup("test_group")

        val id = getGroupId()
        joinGroup(id)

        val response = leaveGroup(id)
        val bodyJsonStr = response.body().toString()
        val responseBody = objectMapper.readValue(bodyJsonStr, GroupResponse::class.java)

        assertEquals("Left group.", responseBody.message)
    }

    private fun leaveGroup(id: String): HttpResponse<String> {
        return doPostRequest("$url/leave", mapOf("groupId" to id), jwt)
    }

    @Test
    fun getGroupListings(){

    }

    @Test
    fun deleteGroup(){

    }

    private fun getGroupId(): String {
        var response = doGetRequest("$url/listings", jwt)
        val groupId = extractIdFromJson(response.body().toString()) ?: error("No group Id found in test request.")
        return groupId
    }

    private fun joinGroup(id: String): HttpResponse<String> {
        return doPostRequest("$url/join", mapOf("groupId" to id), jwt)
    }

    private fun createGroup(groupName: String): HttpResponse<String> {
        return doPostRequest("$url/create", mapOf("groupName" to "$groupName"), jwt)
    }

    private fun extractIdFromJson(jsonStr: String): String? {
        // Parse the JSON string into a JsonNode
        val rootNode: JsonNode = objectMapper.readTree(jsonStr)
        // Extract the id from the first element in the array
        return rootNode.firstOrNull()?.get("id")?.asText()
    }
}