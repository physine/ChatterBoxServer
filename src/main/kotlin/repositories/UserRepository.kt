package physine.repositories

import physine.models.UserModel
import java.util.*

interface UserRepository {
    fun createUser(user: UserModel): UserModel
    fun getUserById(userId: UUID): UserModel?
    fun getUserByUsername(username: String): UserModel?
    fun updateUser(user: UserModel): Boolean
    fun deleteUser(userId: UUID): Boolean
}