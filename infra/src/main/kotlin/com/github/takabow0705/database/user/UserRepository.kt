package com.github.takabow0705.database.user

import com.github.takabow0705.database.RepositoryBase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

interface UserRepository : RepositoryBase {
  suspend fun findAllInActive(): List<User>

  suspend fun findOne(emailAddress: String): User?

  suspend fun createUser(user: User): User?

  suspend fun deleteUser(user: User): Boolean
}

class UserRepositoryImpl : UserRepository {
  override suspend fun findAllInActive(): List<User> {
    return dbQuery { Users.select { Users.isDeleted eq Op.TRUE }.map(::mapToUser).toList() }
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
      val insertStatment =
        Users.insert {
          it[Users.userName] = user.userName
          it[Users.password] = user.password
          it[Users.detail] = user.detail
        }
      insertStatment.resultedValues?.singleOrNull()?.let(::mapToUser)
    }
  }

  /** 指定されたユーザを論理削除します。 */
  override suspend fun deleteUser(user: User): Boolean {
    return dbQuery { Users.update({ Users.id eq user.id }) { it[Users.isDeleted] = false } > 0 }
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
