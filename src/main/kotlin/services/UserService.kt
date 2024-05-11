package physine.services

import physine.dtos.UserDTO
import physine.repositories.UserRepository

class UserService(private val userRepository: UserRepository){

    fun getUser(){
        println("Service - UserService - getUser")
    }

    fun createUser(user: UserDTO){
        println("\tService - UserService - createUser")
        // validate credentials - username and password
        userRepository.create(user)
    }

    fun updateUser(){
        println("Service - UserService - updateUser")
    }
}