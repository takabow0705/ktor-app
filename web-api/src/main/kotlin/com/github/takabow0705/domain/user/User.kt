package com.github.takabow0705.domain.user

import com.github.takabow0705.database.user.UserTable

data class User(
  val id: Int?,
  val emailAddress: String,
  val password: String,
  val userName: String,
  val isDeleted: Boolean,
  val detail: String?
) {
  companion object {
    fun map(userTable: UserTable): User {
      return User(
        id = userTable.id,
        emailAddress = userTable.emailAddress,
        password = userTable.password,
        userName = userTable.userName,
        isDeleted = userTable.isDeleted,
        detail = userTable.detail
      )
    }
  }

  fun disable(): User {
    return User(this.id, this.emailAddress, this.password, this.userName, true, this.detail)
  }

  fun enable(): User {
    return User(this.id, this.emailAddress, this.password, this.userName, false, this.detail)
  }

  fun toTable(): UserTable {
    return UserTable(
      this.id,
      this.emailAddress,
      this.password,
      this.userName,
      this.isDeleted,
      this.detail
    )
  }
}
