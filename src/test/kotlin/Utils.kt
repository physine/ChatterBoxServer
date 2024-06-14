
import ch.qos.logback.classic.Logger
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

val log = LoggerFactory.getLogger("app") as Logger

lateinit var jwt: String
const val baseUrl = "http://localhost:8080"
val objectMapper = ObjectMapper()
val credentials: MutableMap<String, String> = mutableMapOf(
    "username" to "test_user",
    "password" to "test_password"
)

fun doPostRequest(url: String, body: Map<String, String>? = null, jwt: String? = null): HttpResponse<String> {
    val requestBody = body?.let { objectMapper.writeValueAsString(it) } ?: ""
    val client = HttpClient.newBuilder().build()
    val requestBuilder = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))

    jwt?.let {
        requestBuilder.header("Authorization", "Bearer $it")
    }

    val request = requestBuilder.build()
    return client.send(request, HttpResponse.BodyHandlers.ofString())
}

fun doGetRequest(url: String, jwt: String? = null): HttpResponse<String> {
    val client = HttpClient.newBuilder().build()
    val requestBuilder = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()

    jwt?.let {
        requestBuilder.header("Authorization", "Bearer $it")
    }

    val request = requestBuilder.build()
    return client.send(request, HttpResponse.BodyHandlers.ofString())
}

fun startTestEnvironmentConfig(){
    System.setProperty("USE_TEST_DB", "true")
    dropAllRows("users")
    dropAllRows("groups")
}

fun endTestEnvironmentConfig(){
    System.setProperty("USE_TEST_DB", "false")
    dropAllRows("users")
    dropAllRows("groups")
}

suspend fun waitForServer() {
    val client = HttpClient.newBuilder().build()
    while (true) {
        println("Waiting for health check...")
        try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create("${baseUrl}/health-check"))
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

fun dropAllRows(tableName: String) {
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