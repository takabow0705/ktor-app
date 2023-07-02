package com.github.takabow0705.database

import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
  fun init(config: ApplicationConfig) {
    val driverClassName = config.property("storage.driverClassName").getString()
    val jdbcUrl = config.property("storage.jdbcUrl").getString()
    val user = config.property("storage.user").getString()
    val password = config.property("storage.password").getString()
    val db =
      Database.connect(url = jdbcUrl, driver = driverClassName, user = user, password = password)
  }
}
