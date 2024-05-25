package physine.services

import physine.dtos.ChangePasswordDTO
import physine.dtos.CreateUserDTO
import physine.dtos.LoginDTO
import physine.models.UserModel
import physine.models.responces.Response
import java.util.*

interface UserService {
    fun create(createUserDTO: CreateUserDTO): Response
    fun login(loginDTO: LoginDTO): Response
    fun changePassword(changePasswordDTO: ChangePasswordDTO): Unit
    fun delete(uuid: UUID): Unit
    fun getUserInfo(username: String): UserModel?
}