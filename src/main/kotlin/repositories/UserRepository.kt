package physine.repositories

import physine.dtos.CreateUserDTO
import physine.models.UserModel
import java.util.*

interface UserRepository {
    fun create(createUserDTO: CreateUserDTO): Unit
//    fun findById(uuid: UUID)
    fun findByUsername(username: String): UserModel?
    fun findAll()
    fun update()
    fun delete(uuid: UUID)
}