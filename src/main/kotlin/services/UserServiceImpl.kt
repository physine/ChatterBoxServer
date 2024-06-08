package physine.services

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import physine.dtos.ChangePasswordDTO
import physine.dtos.CreateUserDTO
import physine.dtos.LoginDTO
import physine.models.UserModel
import physine.models.responces.UserResponse
import physine.models.responces.UserResponses
import physine.repositories.UserRepository
import physine.services.jwt.JWTService
import physine.utils.CredentialValidation.validateCreds
import physine.utils.CredentialValidation.validatePassword
import java.util.*

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JWTService
) : UserService {

    private val log = LoggerFactory.getLogger("app") as Logger

    override fun create(createUserDTO: CreateUserDTO): UserResponse {
        if (!validateCreds(createUserDTO.username, createUserDTO.password))
            return UserResponses.userCreationNotSuccessful()
        if (!userRepository.isUsernameAvailable(createUserDTO.username))
            return UserResponses.usernameUnavailable()
        val userModel = userRepository.createUser(createUserDTO.toModel())
        val token = jwtService.generateToken(userModel)
        return UserResponses.userCreationSuccessful(token)
    }

    override fun login(loginDTO: LoginDTO): UserResponse {
        val userModel = userRepository.getUserByUsername(loginDTO.username) ?: return UserResponses.logInNotSuccessful()
        log.info("[i] login attempt from ${userModel.username}")
        if (userModel.password != loginDTO.password) // TODO: will need encryption
            return UserResponses.logInNotSuccessful()

        return UserResponses.logInSuccessful(jwtService.generateToken(userModel))
    }

    override fun changePassword(changePasswordDTO: ChangePasswordDTO): UserResponse {
        val userModel = userRepository.getUserById(changePasswordDTO.uuid) ?: return UserResponses.passwordCouldNotBeChanged()
        log.info("[i] Change password request from ${userModel.username}")
        if (!validatePassword(changePasswordDTO.newPassword))
            return UserResponses.invalidPassword()
        userModel.password = changePasswordDTO.newPassword
        userRepository.updateUser(userModel)
        return UserResponses.passwordChanged()
    }

    override fun delete(uuid: UUID): UserResponse {
        log.info("[i] Account deletion attempt from $uuid")
        return if (userRepository.deleteUser(uuid))
            UserResponses.deleteSuccessful()
        else
            UserResponses.deleteNotSuccessful()
    }

    override fun getUserInfo(username: String): UserModel? {
        return userRepository.getUserByUsername(username)
    }
}