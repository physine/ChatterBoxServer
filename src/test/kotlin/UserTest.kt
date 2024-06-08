
import ch.qos.logback.classic.Logger
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import physine.chatterBoxApi.ChatterBox
import physine.models.responces.UserResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {
    private val log = LoggerFactory.getLogger("app") as Logger
    private val url = "http://localhost:8080/user"
    private val objectMapper = ObjectMapper()
    private val requestBodyValues: MutableMap<String, String> = mutableMapOf(
        "username" to "test_user",
        "password" to "test_password"
    )

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            System.setProperty("USE_TEST_DB", "true")
            runBlocking {
                println("setting up test env")
                startServer()
                waitForServer()
            }
        }

        @JvmStatic
        private suspend fun startServer() = coroutineScope {
            GlobalScope.launch {
                ChatterBox().run()
            }
        }

        private suspend fun waitForServer() {
            val client = HttpClient.newBuilder().build()
            val url = "http://localhost:8080/health-check"
            while (true) {
                println("Waiting for health check...")
                try {
                    val request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build()
                    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    if (response.statusCode() == 200) {
                        break
                    }
                } catch (e: Exception) {
                    // Server is not ready yet
                }
                delay(300)
            }
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            System.setProperty("USE_TEST_DB", "false")
            // TODO: clean all db tables
        }
    }

    @BeforeEach
    fun beforeEach(){
        dropAllRows("users")
        requestBodyValues["username"] = "test_user"
        requestBodyValues["password"] = "test_password"
    }

    @Test
    fun createUserTest() {
        val response = doPostRequest(url, requestBodyValues)
        assert(response.body().contains("Account Creation Successful. Logged In."))
    }

    @Test
    fun usernameNotAvailable(){
        doPostRequest(url, requestBodyValues)
        val response = doPostRequest(url, requestBodyValues)
        assert(response.body().contains("Username Not Available."))
    }

    @Test
    fun loginTest(){
        var response = doPostRequest(url, requestBodyValues)
        var bodyJsonStr = response.body().toString()
        var bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)
        log.info(bodyJson.message)
        assert(bodyJson.message == "Account Creation Successful. Logged In.")

        response = doPostRequest("$url/login", requestBodyValues)
        bodyJsonStr = response.body().toString()
        bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)
        assert(bodyJson.message == "Login Successful.")
    }

    @Test
    fun loginNotSuccessfulTest(){
        // create user to login
        var response = doPostRequest(url, requestBodyValues)
        var bodyJsonStr = response.body().toString()
        var bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)
        assert(bodyJson.message == "Account Creation Successful. Logged In.")

        // try login with wrong username and correct password
        requestBodyValues["username"] = "wrong_username"
        response = doPostRequest("$url/login", requestBodyValues)
        bodyJsonStr = response.body().toString()
        bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)
        assert(bodyJson.message == "Login Not Successful. Username and/or password is wrong.")

        // try login with correct username and wrong password
        requestBodyValues["username"] = "test_username"
        requestBodyValues["password"] = "wrong_Password"
        response = doPostRequest("$url/login", requestBodyValues)
        bodyJsonStr = response.body().toString()
        bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)
        assert(bodyJson.message == "Login Not Successful. Username and/or password is wrong.")
    }

    // TODO: create test for changing password

    // TODO: create test for deleting a user

    private fun doPostRequest(url: String, values:  Map<String, String> ) : HttpResponse<String> {
        val requestBody = objectMapper.writeValueAsString(values)
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()
        return client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    private fun dropAllRows(tableName: String) {
        var connection: Connection? = null
        var statement: Statement? = null
        try {
            val url = "jdbc:postgresql://localhost:5432/chatter_box_db_test"
            val user = "dev"
            val password = "dev"
            connection = DriverManager.getConnection(url, user, password)
            statement = connection.createStatement()
            val sql = "TRUNCATE TABLE \"$tableName\""
            println("Executing SQL: $sql")
            statement.executeUpdate(sql)
        } catch (e: SQLException) {
            println("Error executing SQL command:")
            e.printStackTrace()
        } finally {
            // Clean up
            try {
                statement?.close()
                connection?.close()
                println("Resources closed.")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}