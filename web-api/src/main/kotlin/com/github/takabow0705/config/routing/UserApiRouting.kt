package com.github.takabow0705.config.routing

import com.github.takabow0705.domain.UserRegistrationRequest
import com.github.takabow0705.domain.UserUpdateRequest
import com.github.takabow0705.presentation.user.UserApiResource
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userApiRouting(userApiResource: UserApiResource) {
  route("/user") {
    get { call.respond(userApiResource.listUser()) }
    post("register") {
      val userRegistrationRequest = call.receive<UserRegistrationRequest>()
      call.respond(userApiResource.registerUser(userRegistrationRequest))
    }
    post("disable") {
      val userUpdateRequest = call.receive<UserUpdateRequest>()
      call.respond(userApiResource.disableUser(userUpdateRequest))
    }
    post("activate") {
      val userUpdateRequest = call.receive<UserUpdateRequest>()
      call.respond(userApiResource.activateUser(userUpdateRequest))
    }
  }
}
