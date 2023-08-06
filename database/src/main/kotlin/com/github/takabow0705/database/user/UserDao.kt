package com.github.takabow0705.database.user

import com.github.takabow0705.database.DaoBase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface UserDao : DaoBase {
  suspend fun findAll(): List<UserTable>

  suspend fun findOne(emailAddress: String): UserTable?

  suspend fun createUser(userTable: UserTable): UserTable?

  suspend fun updateUser(userTable: UserTable): Boolean
}

class UserDaoImpl : UserDao {
  override suspend fun findAll(): List<UserTable> {
    return dbQuery { Users.selectAll().map(::mapToUser).toList() }
  }

  override suspend fun findOne(emailAddress: String): UserTable? {
    return dbQuery { Users.select { Users.emailAddress eq emailAddress }.map(::mapToUser) }
      .singleOrNull()
  }

  /** ユーザを1件新規登録します。 */
  override suspend fun createUser(userTable: UserTable): UserTable? {
    return dbQuery {
      transaction {
        val insertStatment =
          Users.insert {
            it[Users.emailAddress] = userTable.emailAddress
            it[Users.userName] = userTable.userName
            it[Users.password] = userTable.password
            it[Users.detail] = userTable.detail
          }
        insertStatment.resultedValues?.singleOrNull()?.let(::mapToUser)
      }
    }
  }

  /** 指定されたユーザを論理削除します。 */
  override suspend fun updateUser(userTable: UserTable): Boolean {
    return dbQuery {
      Users.update({ Users.id eq userTable.id!! }) {
        it[Users.emailAddress] = userTable.emailAddress
        it[Users.password] = userTable.password
        it[Users.userName] = userTable.userName
        it[Users.isDeleted] = userTable.isDeleted
        it[Users.detail] = userTable.detail
      } > 0
    }
  }

  private fun mapToUser(row: ResultRow): UserTable =
    UserTable(
      id = row[Users.id],
      emailAddress = row[Users.emailAddress],
      userName = row[Users.userName],
      isDeleted = row[Users.isDeleted],
      detail = row[Users.detail],
      password = row[Users.password]
    )
}
