package com.github.takabow0705.presentation.user

import com.github.takabow0705.domain.user.User
import com.github.takabow0705.usecase.user.UserManagementService
import javax.inject.Inject

interface UserApiResource {
  fun registerUser(req: UserRegistrationRequest): UserRegistrationResponse

  fun disableUser(req: UserUpdateRequest): UserUpdateResponse

  fun activateUser(req: UserUpdateRequest): UserUpdateResponse

  fun listUser(): UserListResponse
}

class UserApiV1Resource
@Inject
constructor(private val userManagementService: UserManagementService) : UserApiResource {
  override fun registerUser(req: UserRegistrationRequest): UserRegistrationResponse {
    return runCatching {
        userManagementService.register(
          User(
            id = null,
            userName = req.userName,
            emailAddress = req.mailAddress,
            isDeleted = false,
            password = req.password,
            detail = req.detail
          )
        )
      }
      .onFailure { ex ->
        return UserRegistrationResponse(UserApiStatus.NG, "ユーザの登録に失敗しました。")
      }
      .recoverCatching { UserRegistrationResponse(UserApiStatus.NG, "ユーザの登録に失敗しました。") }
      .mapCatching { UserRegistrationResponse(UserApiStatus.OK, "") }
      .getOrDefault(UserRegistrationResponse(UserApiStatus.NG, "予期せぬ例外が発生しました。"))
  }

  override fun disableUser(req: UserUpdateRequest): UserUpdateResponse {
    return runCatching { userManagementService.disableUser(req.mailAddress) }
      .recoverCatching { UserUpdateResponse(UserApiStatus.NG, "ユーザの更新に失敗しました。") }
      .mapCatching { UserUpdateResponse(UserApiStatus.OK, "") }
      .getOrDefault(UserUpdateResponse(UserApiStatus.NG, "予期せぬ例外が発生しました。"))
  }

  override fun activateUser(req: UserUpdateRequest): UserUpdateResponse {
    return runCatching { userManagementService.enableUser(req.mailAddress) }
      .recoverCatching { UserUpdateResponse(UserApiStatus.NG, "ユーザの更新に失敗しました。") }
      .mapCatching { UserUpdateResponse(UserApiStatus.OK, "") }
      .getOrDefault(UserUpdateResponse(UserApiStatus.NG, "予期せぬ例外が発生しました。"))
  }

  override fun listUser(): UserListResponse {
    return UserListResponse(
      UserApiStatus.OK,
      userManagementService.list().map { u -> UserApiView.map(u) }
    )
  }
}
