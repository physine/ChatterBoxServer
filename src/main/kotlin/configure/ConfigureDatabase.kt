package physine.configure

import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import physine.db.UserTable

fun configureDatabase() {

    val dotenv = Dotenv.configure()
        .directory("./")
        .load()

    val dbHost = dotenv["DB_HOST"] ?: error("DB_HOST not set")
    val dbPort = dotenv["DB_PORT"] ?: error("DB_PORT not set")
    val dbUser = dotenv["POSTGRES_USERNAME_DEV"] ?: error("POSTGRES_USERNAME_DEV not set")
    val dbPassword = dotenv["POSTGRES_PASSWORD_DEV"] ?: error("POSTGRES_PASSWORD_DEV not set")
    val useTestDb = dotenv["USE_TEST_DB"] ?: error("USE_TEST_DB not set")
    val testDbName = dotenv["POSTGRES_DB_TEST"] ?: error("POSTGRES_DB_TEST not set")
    val devDbName = dotenv["POSTGRES_DB_DEV"] ?: error("POSTGRES_DB_DEV not set")
    val dbName = if (useTestDb == "true") testDbName else devDbName
    val dbUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(UserTable)
    }
}