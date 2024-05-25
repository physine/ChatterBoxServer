package physine.db

import physine.models.UserModel

class InMemoryImpl: DB {

    private val db: MutableMap<String, UserModel> = mutableMapOf()

    override fun save(userModel: UserModel) {
        db[userModel.username] = userModel
        println(db.toString())
    }

    override fun findByUsername(username: String): UserModel? {
        val userModel =  db[username]
        println(userModel.toString())
        return userModel
    }
}