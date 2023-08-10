package com.github.takabow0705.presentation.productmaster

import io.ktor.http.content.*
import kotlinx.serialization.Serializable

@Serializable data class EquityMasterUploadRequest(val multiPartData: MultiPartData)

@Serializable data class EquityIndexFuturesMasterUploadRequest(val multiPartData: MultiPartData)

@Serializable
data class EquityIndexFuturesOptionMasterUploadRequest(val multiPartData: MultiPartData)

@Serializable data class CurrencyMasterUploadRequest(val multiPartData: MultiPartData)
