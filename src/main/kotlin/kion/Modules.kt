package physine.kion

import org.koin.dsl.module
import physine.repositories.UserRepository
import physine.services.UserService

val appModule = module {
    single { UserService(get()) }
    single { UserRepository() }
}