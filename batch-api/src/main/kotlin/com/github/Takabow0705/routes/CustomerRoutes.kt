package com.github.Takabow0705.routes

import com.github.Takabow0705.example.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
  route("/customer") {
    get {
      if (customerStorage.isNotEmpty()) {
        call.respond(customerStorage)
      } else {
        call.respondText("No customer found", status = HttpStatusCode.OK)
      }
    }
    get("{id?}") {
      val id =
        call.parameters["id"]
          ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
      val customer =
        customerStorage.find { it.id == id }
          ?: return@get call.respondText(
            "No customer with id $id",
            status = HttpStatusCode.NotFound
          )
      call.respond(customer)
    }
    post {
      try {
        val customer = call.receive<Customer>()
        customerStorage.add(customer)
        call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
      } catch (e: Exception) {
        e.printStackTrace()
        print(e.message)
      }
    }
    delete("{id?}") {
      val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
      if (customerStorage.removeIf { it.id == id }) {
        call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
      } else {
        call.respondText("Not Found", status = HttpStatusCode.NotFound)
      }
    }
  }
}
