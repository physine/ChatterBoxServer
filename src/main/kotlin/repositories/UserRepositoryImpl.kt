package physine.repositories

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import physine.db.UserTable
import physine.models.UserModel
import java.util.*

class UserRepositoryImpl(): UserRepository {
    override fun createUser(user: UserModel): UserModel {
//        println("[i] Starting transaction $this")
        transaction {
//            println("[i] Starting insert $this")
            UserTable.insert {
                it[id] = user.uuid
                it[username] = user.username
                it[password] = user.password
//                println("[i] Complete insert $this")
            }
        }
//        println("[i] Complete transaction $this")
        return user
    }

    override fun getUserById(userId: UUID): UserModel? {
        TODO("Not yet implemented")
    }

    override fun getUserByUsername(username: String): UserModel? {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: UserModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: UUID): Boolean {
        TODO("Not yet implemented")
    }
}