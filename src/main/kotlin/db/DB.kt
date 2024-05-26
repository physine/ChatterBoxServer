package physine.db

import physine.models.UserModel

interface DB {
    fun save(userModel: UserModel)
    //fun getById(uuid: UUID): UserModel?
    fun findByUsername(username: String): UserModel?
    fun changePassword(username: String, password: String)
}