package physine.services

import physine.dtos.ChangePasswordDTO
import physine.dtos.CreateUserDTO
import physine.dtos.LoginDTO
import physine.models.UserModel
import physine.models.responces.UserResponse
import java.util.*

interface UserService {
    fun create(createUserDTO: CreateUserDTO): UserResponse
    fun login(loginDTO: LoginDTO): UserResponse
    fun changePassword(changePasswordDTO: ChangePasswordDTO): UserResponse
    fun delete(uuid: UUID): UserResponse
    fun getUserInfo(username: String): UserModel?
}