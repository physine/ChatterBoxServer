
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
import physine.models.responces.UserResponse

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {

    private val url = "$baseUrl/user"

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            startTestEnvironmentConfig()
            runBlocking {
                startServer()
                waitForServer()
            }
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            endTestEnvironmentConfig()
            // TODO: clear all db tables
            dropAllRows("users")
            dropAllRows("groups")
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
        dropAllRows("users")
        credentials["username"] = "test_user"
        credentials["password"] = "test_password"
    }

    @Test
    fun createUser() {
        val response = doPostRequest(url, credentials)
        assert(response.body().contains("Account Creation Successful. Logged In."))
    }

    @Test
    fun usernameNotAvailable(){
        doPostRequest(url, credentials)
        val response = doPostRequest(url, credentials)

        assert(response.body().contains("Username Not Available."))
    }

    @Test
    fun login(){
        var response = doPostRequest(url, credentials)
        var bodyJsonStr = response.body().toString()
        var bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)

        assert(bodyJson.message == "Account Creation Successful. Logged In.")

        response = doPostRequest("${url}/login", credentials)
        bodyJsonStr = response.body().toString()
        bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)

        assert(bodyJson.message == "Login Successful.")
    }

    @Test
    fun loginNotSuccessful(){
        // create user to login
        var response = doPostRequest(url, credentials)
        var bodyJsonStr = response.body().toString()
        var bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)

        assert(bodyJson.message == "Account Creation Successful. Logged In.")

        // try login with wrong username and correct password
        credentials["username"] = "wrong_username"
        response = doPostRequest("${url}/login", credentials)
        bodyJsonStr = response.body().toString()
        bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)

        assert(bodyJson.message == "Login Not Successful. Username and/or password is wrong.")

        // try login with correct username and wrong password
        credentials["username"] = "test_username"
        credentials["password"] = "wrong_Password"
        response = doPostRequest("${url}/login", credentials)
        bodyJsonStr = response.body().toString()
        bodyJson = objectMapper.readValue(bodyJsonStr, UserResponse::class.java)

        assert(bodyJson.message == "Login Not Successful. Username and/or password is wrong.")
    }

    // TODO: create test for changing password

    // TODO: create test for deleting a user
}