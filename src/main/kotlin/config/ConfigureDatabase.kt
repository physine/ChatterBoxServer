package physine.config

import org.jetbrains.exposed.sql.Database

object ConfigureDatabase {
    fun init() {
        val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/chatter_box_db_dev"
        val dbUser = System.getenv("DB_USER") ?: "dev"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "dev"

        Database.connect(
            url = dbUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )
    }
}