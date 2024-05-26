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
        if(!validate(createUserDTO.username, createUserDTO.password))
            return UserResponses.userCreationNotSuccessful()
        if(!isUsernameAvailable(createUserDTO))
            return UserResponses.usernameUnavailable()
        println("[i] Calling Repo $this")
        val userModel = userRepository.createUser(createUserDTO.toModel())
        println("[i] Called Repo $this")
        val token = jwtService.generateToken(userModel)
        return UserResponses.userCreationSuccessful(token)
    }

    override fun login(loginDTO: LoginDTO): Response {
        val userModel = userRepository.getUserByUsername(loginDTO.username)
        if(userModel == null || (userModel.password != loginDTO.password))
            return UserResponses.logInNotSuccessful()
        return UserResponses.logInSuccessful(jwtService.generateToken(userModel))
    }

    override fun changePassword(changePasswordDTO: ChangePasswordDTO): Response {
        // TODO: validate new password first
        val userModel = userRepository.getUserById(changePasswordDTO.uuid)
        if(userModel == null)
            return UserResponses.passwordCouldNotBeChanged()
        userModel.password = changePasswordDTO.newPassword
        userRepository.updateUser(userModel)
        return UserResponses.passwordChanged()
    }

    override fun delete(uuid: UUID): Response {
        return if ( userRepository.deleteUser(uuid) )
                UserResponses.deleteSuccessful()
                else
                UserResponses.deleteNotSuccessful()
    }

    override fun getUserInfo(username: String): UserModel? {
        return userRepository.getUserByUsername(username)
    }
}