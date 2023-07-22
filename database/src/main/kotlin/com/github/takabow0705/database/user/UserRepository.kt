package com.github.takabow0705.database.user

import com.github.takabow0705.database.RepositoryBase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface UserRepository : RepositoryBase {
  suspend fun findAll(): List<User>

  suspend fun findOne(emailAddress: String): User?

  suspend fun createUser(user: User): User?

  suspend fun updateUser(user: User): Boolean
}

class UserRepositoryImpl : UserRepository {
  override suspend fun findAll(): List<User> {
    return dbQuery { Users.selectAll().map(::mapToUser).toList() }
  }

  override suspend fun findOne(emailAddress: String): User? {
    return dbQuery {
        Users.select { Users.emailAddress eq emailAddress and Users.isDeleted eq Op.FALSE }
          .map(::mapToUser)
      }
      .singleOrNull()
  }

  /** ユーザを1件新規登録します。 */
  override suspend fun createUser(user: User): User? {
    return dbQuery {
      transaction {
        val insertStatment =
          Users.insert {
            it[Users.userName] = user.userName
            it[Users.password] = user.password
            it[Users.detail] = user.detail
          }
        insertStatment.resultedValues?.singleOrNull()?.let(::mapToUser)
      }
    }
  }

  /** 指定されたユーザを論理削除します。 */
  override suspend fun updateUser(user: User): Boolean {
    return dbQuery {
      Users.update({ Users.id eq user.id!! }) {
        it[Users.emailAddress] = user.emailAddress
        it[Users.password] = user.password
        it[Users.userName] = user.userName
        it[Users.isDeleted] = user.isDeleted
        it[Users.detail] = user.detail
      } > 0
    }
  }

  private fun mapToUser(row: ResultRow): User =
    User(
      id = row[Users.id],
      emailAddress = row[Users.emailAddress],
      userName = row[Users.userName],
      isDeleted = row[Users.isDeleted],
      detail = row[Users.detail],
      password = row[Users.password]
    )
}
