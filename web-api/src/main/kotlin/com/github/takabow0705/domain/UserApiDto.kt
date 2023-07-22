package com.github.takabow0705.domain

import com.github.takabow0705.database.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
  val userName: String,
  val password: String,
  val mailAddress: String,
  val detail: String?
)

@Serializable data class UserRegistrationResponse(val status: UserApiStatus, val message: String)

@Serializable data class UserUpdateRequest(val mailAddress: String)

@Serializable data class UserUpdateResponse(val status: UserApiStatus, val message: String?)

@Serializable data class UserListResponse(val status: UserApiStatus, val list: List<UserApiView>)

@Serializable
data class UserApiView(
  val userName: String,
  val mailAddress: String,
  val detail: String?,
  val isDeleted: Boolean
) {
  companion object {
    fun map(user: User): UserApiView {
      return UserApiView(user.userName, user.emailAddress, user.detail, user.isDeleted)
    }
  }
}

enum class UserApiStatus {
  OK,
  NG
}
