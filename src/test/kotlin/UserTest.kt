
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
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {
    val log = LoggerFactory.getLogger("app") as Logger
    private val url = "http://localhost:8080/user"

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
        // clear all rows in test db
        dropAllRows("User")
    }

    @Test
    fun loginTest() {
        log.info("loginTest")
        val values = mapOf(
            "username" to "user",
            "password" to "password123"
        )

        val objectMapper = ObjectMapper()
        val requestBody = objectMapper.writeValueAsString(values)
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
        assert(response.body().contains("Account Creation Successful."))
        assertEquals(200, response.statusCode())
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