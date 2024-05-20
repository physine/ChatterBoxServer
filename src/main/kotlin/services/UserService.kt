package physine.services

import physine.dtos.UserDTO
import physine.models.responces.Response
import physine.models.responces.UserResponses
import physine.repositories.UserRepository
import physine.services.jwt.JWTService
import physine.utils.CredentialValidation

class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JWTService
){

    fun getUser(){
        println("Service - UserService - getUser")
    }

    fun createUser(userDTO: UserDTO): Response {
        println("\tService - UserService - createUser")

        if(!CredentialValidation.validate(userDTO)){
        }

        if(!CredentialValidation.checkUsernameAvailability(userDTO.username)){
            return UserResponses.usernameUnavailable()
        }

        userRepository.create(userDTO)
        val user = userRepository.findByUsername(userDTO.username)
        val token = jwtService.generateToken(user)
        return UserResponses.userCreationSuccessful(token)
    }

    fun userLogin(userDTO: UserDTO): Response {
        if(CredentialValidation.verifyUserCredentialFormats(userDTO)){
        }

        val userDTO = userRepository.findByUsername(userDTO.username)
        if (userDTO == null){
            return UserResponses.logInNotSuccessful()
        }

        // check if there is already a valid jwt for the user

        val user = userRepository.findByUsername(userDTO.username)
        if(user == null){
            return UserResponses.logInNotSuccessful()
        }

        println("doing jwt:")
        println( "jwt:\t${jwtService.generateToken(user)}" )
        return UserResponses.logInSuccessful()
    }

    fun updateUser(){
        println("Service - UserService - updateUser")
    }
}