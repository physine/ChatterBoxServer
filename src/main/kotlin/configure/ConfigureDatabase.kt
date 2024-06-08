package physine.configure

import ch.qos.logback.classic.Logger
import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import physine.db.GroupsTable
import physine.db.MessagesTable
import physine.db.UsersTable

fun configureDatabase() {
    val log = LoggerFactory.getLogger("app") as Logger
    val dotenv = Dotenv.configure()
        .directory("./")
        .load()

    val dbHost = dotenv["DB_HOST"] ?: error("DB_HOST not set")
    val dbPort = dotenv["DB_PORT"] ?: error("DB_PORT not set")
    val dbUser = dotenv["POSTGRES_USERNAME_DEV"] ?: error("POSTGRES_USERNAME_DEV not set")
    val dbPassword = dotenv["POSTGRES_PASSWORD_DEV"] ?: error("POSTGRES_PASSWORD_DEV not set")
    val testDbName = dotenv["POSTGRES_DB_TEST"] ?: error("POSTGRES_DB_TEST not set")
    val devDbName = dotenv["POSTGRES_DB_DEV"] ?: error("POSTGRES_DB_DEV not set")
    val useTestDb = System.getProperty("USE_TEST_DB") ?: "false"
    val dbName = if (useTestDb == "true") testDbName else devDbName
    val dbUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

    log.info(dbUrl)

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(UsersTable)
        SchemaUtils.create(GroupsTable)
        SchemaUtils.create(MessagesTable)
    }
}