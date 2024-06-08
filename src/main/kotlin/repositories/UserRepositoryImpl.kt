package physine.repositories

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import physine.db.UsersTable
import physine.db.toUserModel
import physine.models.UserModel
import java.util.*

class UserRepositoryImpl() : UserRepository {
    override fun createUser(user: UserModel): UserModel {
        transaction {
            UsersTable.insert {
                it[userId] = user.uuid
                it[username] = user.username
                it[password] = user.password
            }
        }
        return user
    }

    override fun getUserById(userId: UUID): UserModel? {
        return transaction {
            UsersTable
                .slice(UsersTable.userId, UsersTable.username, UsersTable.password)
                .select(UsersTable.userId eq userId)
                .mapNotNull { it.toUserModel() }
                .singleOrNull()
        }
    }

    override fun getUserByUsername(username: String): UserModel? {
        return transaction {
            UsersTable
                .slice(UsersTable.userId, UsersTable.username, UsersTable.password)
                .select(UsersTable.username eq username)
                .mapNotNull { it.toUserModel() }
                .singleOrNull()
        }
    }

    override fun updateUser(user: UserModel): Boolean {
        return transaction {
            UsersTable.update({ UsersTable.userId eq user.uuid }) {
                it[password] = user.password
            } > 0
        }
    }

    override fun isUsernameAvailable(username: String): Boolean {
        return transaction {
            val count = UsersTable.select ( UsersTable.username eq username ).count()
            count.toInt() == 0
        }
    }

    override fun deleteUser(userId: UUID): Boolean {
        return transaction {
            UsersTable.deleteWhere {
                UsersTable.userId eq userId
            }
        } > 0
    }
}