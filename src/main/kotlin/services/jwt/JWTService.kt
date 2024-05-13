package physine.services.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import physine.models.UserModel

class JWTService(private val config: HoconApplicationConfig) : IJWTManagementService {

    override fun generateToken(user: UserModel): String {
        val audience = config.property("jwt.audience").getString()
        val secret = config.property("jwt.secret").getString()
        val issuer = config.property("jwt.issuer").getString()
        val realm = config.property("jwt.realm").getString()
        val expiration = System.currentTimeMillis() * 60_000

        val token = JWT.create()
            .withClaim("username", user.username)
            .withClaim("uuid", user.uuid.toString())
            .withClaim("expiration", expiration.toString())
            .withIssuer(issuer)
            .withAudience(audience)
            .sign(Algorithm.HMAC256(secret))
        return token
    }
}