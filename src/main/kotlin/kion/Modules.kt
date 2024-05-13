package physine.kion

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.dsl.module
import physine.repositories.UserRepository
import physine.services.UserService
import physine.services.jwt.JWTService

val appModule = module {
    single { HoconApplicationConfig( ConfigFactory.load() ) }
    single { UserRepository() }
    single { JWTService( get() ) }
    single { UserService( get(), get() ) }
}