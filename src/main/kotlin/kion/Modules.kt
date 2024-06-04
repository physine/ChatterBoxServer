package physine.kion

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.dsl.module
import physine.chatterBoxApi.ChatterBox
import physine.repositories.UserRepository
import physine.repositories.UserRepositoryImpl
import physine.services.UserService
import physine.services.UserServiceImpl
import physine.services.jwt.JWTService
import physine.services.jwt.JWTServiceImpl

val appModule = module {
    single { ChatterBox() }
    single { HoconApplicationConfig(ConfigFactory.load()) }
    single<UserRepository> { UserRepositoryImpl() }
    single<JWTService> { JWTServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get(), get()) }
}