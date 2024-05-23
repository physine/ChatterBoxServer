package physine.services

import physine.dtos.UserDTO
import physine.models.responces.Response

interface UserService {
    fun createUser(userDTO: UserDTO): Response
    fun userLogin(userDTO: UserDTO): Response
    fun updateUser(userDTO: UserDTO): Unit
}