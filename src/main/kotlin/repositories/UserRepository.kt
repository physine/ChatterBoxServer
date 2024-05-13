package physine.repositories

import physine.dtos.UserDTO
import physine.models.UserModel
import java.util.*

class UserRepository() {

    fun create(user: UserDTO){
        println("\t\tRepository - UserRepository - create")
    }

    fun findById(){

    }

    fun findByUsername(username: String): UserModel {
        return UserModel("uname","pw", UUID.randomUUID())
    }

    fun findAll(){

    }

    fun update(){

    }

    fun delete(){

    }
}