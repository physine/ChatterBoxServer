package physine.services.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.server.config.*
import physine.models.UserModel
import java.util.*

class JWTServiceImpl(private val config: HoconApplicationConfig) : JWTService {

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
}