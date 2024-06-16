package physine.services.jwt

import com.auth0.jwt.interfaces.JWTVerifier
import physine.models.UserModel
import java.util.*

interface JWTService {
    val realm: String
    fun generateToken(user: UserModel): String
    fun generateVerifier(): JWTVerifier
    fun isValidToken(token: String): Boolean
    fun getUserIdFromToken(token: String): UUID
    fun getUsernameFromToken(token: String): String
}