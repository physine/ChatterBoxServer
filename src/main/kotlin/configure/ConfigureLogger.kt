package physine.configure

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

fun configureLogger() {
    val log = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as Logger
    log.level = Level.WARN

    val routesLog = LoggerFactory.getLogger("app") as Logger
    routesLog.level = Level.INFO
}