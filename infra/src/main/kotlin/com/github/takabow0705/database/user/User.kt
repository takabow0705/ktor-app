package com.github.takabow0705.database.user

import org.jetbrains.exposed.sql.Table

data class User(
  val id: Int,
  val emailAddress: String,
  val password: String,
  val userName: String,
  val isDeleted: Boolean,
  val detail: String?
)

object Users : Table() {
  val id = integer("id").autoIncrement()
  val emailAddress = varchar("email_address", 500).uniqueIndex("pk_users_emailAddress")
  val password = varchar("password", 500)
  val userName = varchar("user_name", 500)
  val isDeleted = bool("is_deleted").default(false)
  val detail = text("detail").nullable()

  override val primaryKey = PrimaryKey(id)
}
