package physine.services.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.server.config.*
import physine.models.UserModel
import java.util.*

class JWTServiceImpl(config: HoconApplicationConfig) : JWTService {

    private val audience = config.property("jwt.audience").getString()
    private val issuer = config.property("jwt.issuer").getString()
    private val secret = config.property("jwt.secret").getString()
    override val realm = config.property("jwt.realm").getString()

    override fun generateToken(userModel: UserModel): String {
        val expiration = Date(System.currentTimeMillis() + 6000000)
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", userModel.username)
            .withClaim("uuid", userModel.uuid.toString())
            .withExpiresAt(expiration)
            .sign(Algorithm.HMAC256(secret))
        return token
    }

    override fun generateVerifier(): JWTVerifier {
        val verifier = JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
        return verifier
    }

    override fun isValidToken(token: String): Boolean {
        return try {
            val verifier = generateVerifier()
            verifier.verify(token)
            true
        } catch (exception: Exception) {
            false
        }
    }

    override fun getUserIdFromToken(token: String): UUID {
        val verifier = generateVerifier()
        val decodedJWT = verifier.verify(token)
        return UUID.fromString(decodedJWT.getClaim("uuid").asString())
    }

    override fun getUsernameFromToken(token: String): String {
        val verifier = generateVerifier()
        val decodedJWT = verifier.verify(token)
        return decodedJWT.getClaim("username").asString()
    }
}