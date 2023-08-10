package com.github.takabow0705.config

import com.github.takabow0705.config.routing.productApiRouting
import com.github.takabow0705.config.routing.userApiRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
  routing { userApiRouting(DaggerApiResourceComponent.create().userApiResource()) }
  routing { productApiRouting(DaggerApiResourceComponent.create().productMasterApiResource()) }
}
