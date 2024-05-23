package physine.repositories

import physine.dtos.UserDTO
import physine.models.UserModel
import java.util.*

class UserRepositoryImpl(): UserRepository {

    override fun create(userDTO: UserDTO){
        println("\t\tRepository - UserRepository - create")
    }

    override fun findById(userDTO: UserDTO) {

    }

    override fun findByUsername(username: String): UserModel {
        return UserModel("uname","pw", UUID.randomUUID())
    }

    override fun findAll() {

    }

    override fun update() {

    }

    override fun delete() {

    }
}