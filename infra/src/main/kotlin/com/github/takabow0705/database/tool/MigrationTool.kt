package com.github.takabow0705.database.tool

import com.github.takabow0705.database.DatabaseFactory
import com.github.takabow0705.database.user.Users
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.util.*

internal val logger = LoggerFactory.getLogger("DatabaseMigration")

fun main(args: Array<String>) {
  val env = System.getProperty("app.env")
  val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("migration.properties")
  val properties = Properties()
  properties.load(inputStream)

  val jdbcUrl = properties.getProperty("$env.storage.jdbcUrl")
  val driverClass = properties.getProperty("$env.storage.driverClassName")
  val userName = properties.getProperty("$env.storage.user")
  val password = properties.getProperty("$env.storage.password")
  val db = Database.connect(jdbcUrl, driver = driverClass, user = userName, password = password)

  transaction(db) {
    logger.info("Start DB Migration. target: {}, user: {}", jdbcUrl, userName)
    SchemaUtils.create(Users)
    logger.info("Finish DB Migration. target: {}, user: {}", jdbcUrl, userName)
  }
}