package com.github.takabow0705.config.routing

import com.github.takabow0705.presentation.productmaster.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productApiRouting(productMasterApiResource: ProductMasterApiResource) {
  route("/product-master") {
    get("equity") {
      call.response.header(
        HttpHeaders.ContentDisposition,
        ContentDisposition.Attachment.withParameter(
            ContentDisposition.Parameters.FileName,
            "EquityMaster.csv"
          )
          .toString()
      )
      call.respondBytes(productMasterApiResource.downloadEquityMaster().toByteArray())
    }

    get("equity-index-futures") {
      call.response.header(
        HttpHeaders.ContentDisposition,
        ContentDisposition.Attachment.withParameter(
            ContentDisposition.Parameters.FileName,
            "EquityIndexFuturesMaster.csv"
          )
          .toString()
      )
      call.respondBytes(productMasterApiResource.downloadEquityMaster().toByteArray())
    }

    get("equity-index-futures-option") {
      call.response.header(
        HttpHeaders.ContentDisposition,
        ContentDisposition.Attachment.withParameter(
            ContentDisposition.Parameters.FileName,
            "EquityIndexFuturesOptionMaster.csv"
          )
          .toString()
      )
      call.respondBytes(productMasterApiResource.downloadEquityMaster().toByteArray())
    }

    get("currency") {
      call.response.header(
        HttpHeaders.ContentDisposition,
        ContentDisposition.Attachment.withParameter(
            ContentDisposition.Parameters.FileName,
            "Currency.csv"
          )
          .toString()
      )
      call.respondBytes(productMasterApiResource.downloadEquityMaster().toByteArray())
    }

    post("equity/upload") {
      runCatching {
          productMasterApiResource.uploadEquityMaster(
            EquityMasterUploadRequest(call.receiveMultipart())
          )
        }
        .onSuccess { call.respond(HttpStatusCode.OK) }
        .onFailure { ex -> call.respond(HttpStatusCode.BadRequest, ex.message.toString()) }
    }

    post("equity-index-futures/upload") {
      runCatching {
          productMasterApiResource.uploadEquityIndexFuturesMaster(
            EquityIndexFuturesMasterUploadRequest(call.receiveMultipart())
          )
        }
        .onSuccess { call.respond(HttpStatusCode.OK) }
        .onFailure { ex -> call.respond(HttpStatusCode.BadRequest, ex.message.toString()) }
    }

    post("equity-index-futures-option/upload") {
      runCatching {
          productMasterApiResource.uploadEquityIndexFuturesOptionMaster(
            EquityIndexFuturesOptionMasterUploadRequest(call.receiveMultipart())
          )
        }
        .onSuccess { call.respond(HttpStatusCode.OK) }
        .onFailure { ex -> call.respond(HttpStatusCode.BadRequest, ex.message.toString()) }
    }

    post("currency/upload") {
      runCatching {
          productMasterApiResource.uploadCurrencyMaster(
            CurrencyMasterUploadRequest(call.receiveMultipart())
          )
        }
        .onSuccess { call.respond(HttpStatusCode.OK) }
        .onFailure { ex -> call.respond(HttpStatusCode.BadRequest, ex.message.toString()) }
    }
  }
}
