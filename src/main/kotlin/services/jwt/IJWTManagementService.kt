package physine.services.jwt

import physine.models.UserModel

interface IJWTManagementService {
    fun generateToken(user: UserModel): String
}