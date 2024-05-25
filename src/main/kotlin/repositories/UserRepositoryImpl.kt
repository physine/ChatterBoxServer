package physine.repositories

import physine.db.DB
import physine.dtos.CreateUserDTO
import physine.models.UserModel
import java.util.*

class UserRepositoryImpl(private val db: DB): UserRepository {

    override fun create(createUserDTO: CreateUserDTO){
        val userModel = UserModel(createUserDTO.username, createUserDTO.password, UUID.randomUUID())
        db.save(userModel)
    }

//    override fun findById(uuid: UUID): UserModel? {
//        return db.getById(uuid)
//    }

    override fun findByUsername(username: String): UserModel? {
        return db.findByUsername(username)
    }

    override fun findAll() {

    }

    override fun update() {

    }

    override fun delete(uuid: UUID) {
        TODO("Not yet implemented")
    }
}