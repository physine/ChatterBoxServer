package physine.services.jwt

import com.auth0.jwt.interfaces.JWTVerifier
import physine.models.UserModel

interface JWTService {
    val realm: String
    fun generateToken(user: UserModel): String
    fun generateVerifier(): JWTVerifier
}