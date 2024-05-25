package physine.services

import physine.dtos.ChangePasswordDTO
import physine.dtos.CreateUserDTO
import physine.dtos.LoginDTO
import physine.models.UserModel
import physine.models.responces.Response
import physine.models.responces.UserResponses
import physine.repositories.UserRepository
import physine.services.jwt.JWTService
import physine.utils.CredentialValidation.isUsernameAvailable
import physine.utils.CredentialValidation.validate
import java.util.*

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JWTService
): UserService {

    override fun create(createUserDTO: CreateUserDTO): Response {
        if(!validate(createUserDTO.username, createUserDTO.password)){
        }
        if(!isUsernameAvailable(createUserDTO)){
            return UserResponses.usernameUnavailable()
        }
        userRepository.create(createUserDTO)
        val userModel = userRepository.findByUsername(createUserDTO.username)
        if(userModel == null){
            return UserResponses.logInNotSuccessful()
        }
        val token = jwtService.generateToken(userModel)
        return UserResponses.userCreationSuccessful(token)
    }

    override fun login(loginDTO: LoginDTO): Response {
        val userModel = userRepository.findByUsername(loginDTO.username)
        if(userModel == null) return UserResponses.logInNotSuccessful()

        if(userModel.passwordHash != loginDTO.password)
            return UserResponses.logInNotSuccessful()

        if (userModel == null)
            return UserResponses.logInNotSuccessful()
        return UserResponses.logInSuccessful(jwtService.generateToken(userModel))
    }

    override fun changePassword(changePasswordDTO: ChangePasswordDTO) {
        userRepository.update();
    }

    override fun delete(uuid: UUID){
        return userRepository.delete(uuid);
    }

    override fun getUserInfo(username: String): UserModel? {
        return userRepository.findByUsername(username)
    }
}