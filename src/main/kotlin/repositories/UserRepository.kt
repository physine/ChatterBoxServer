package physine.repositories

import physine.dtos.UserDTO
import physine.models.UserModel

interface UserRepository {
    fun create(user: UserDTO): Unit
    fun findById(userDTO: UserDTO)
    fun findByUsername(username: String): UserModel
    fun findAll()
    fun update()
    fun delete()
}