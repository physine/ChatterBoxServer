package physine.services

import physine.dtos.UserDTO
import physine.models.responces.Response
import physine.models.responces.UserResponses
import physine.repositories.UserRepository
import physine.services.jwt.JWTService
import physine.utils.CredentialValidation

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JWTService
): UserService {

    override fun createUser(userDTO: UserDTO): Response {
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

    override fun userLogin(userDTO: UserDTO): Response {
        if(CredentialValidation.verifyUserCredentialFormats(userDTO)){
        }

        val user = userRepository.findByUsername(userDTO.username)
        if (user == null){
            return UserResponses.logInNotSuccessful()
        }

        return UserResponses.logInSuccessful(jwtService.generateToken(user))
    }

    override fun updateUser(userDTO: UserDTO) {
        println("Service - UserService - updateUser")
    }
}