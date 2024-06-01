package physine.repositories

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import physine.db.UserTable
import physine.db.toUserModel
import physine.models.UserModel
import java.util.*

class UserRepositoryImpl() : UserRepository {
    override fun createUser(user: UserModel): UserModel {
        transaction {
            UserTable.insert {
                it[id] = user.uuid
                it[username] = user.username
                it[password] = user.password
            }
        }
        return user
    }

    override fun getUserById(userId: UUID): UserModel? {
        return transaction {
            UserTable
                .slice(UserTable.id, UserTable.username, UserTable.password)
                .select(UserTable.id eq userId)
                .mapNotNull { it.toUserModel() }
                .singleOrNull()
        }
    }

    override fun getUserByUsername(username: String): UserModel? {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: UserModel): Boolean {
        return transaction {
            UserTable.update({ UserTable.id eq user.uuid }) {
                it[password] = user.password
            } > 0
        }
    }

    override fun isUsernameAvailable(username: String): Boolean {
        return transaction {
            val count = UserTable.select ( UserTable.username eq username ).count()
            count.toInt() == 0
        }
    }

    override fun deleteUser(userId: UUID): Boolean {
        return transaction {
            UserTable.deleteWhere {
                UserTable.id eq userId
            }
        } > 0
    }
}