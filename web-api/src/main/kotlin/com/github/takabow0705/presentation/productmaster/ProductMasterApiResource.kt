package com.github.takabow0705.presentation.productmaster

import com.github.takabow0705.usecase.productmaster.*
import io.ktor.http.content.*
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

interface ProductMasterApiResource {
  fun uploadEquityMaster(equityMasterUploadRequest: EquityMasterUploadRequest)

  fun uploadEquityIndexFuturesMaster(
    equityIndexFuturesMasterUploadRequest: EquityIndexFuturesMasterUploadRequest
  )

  fun uploadEquityIndexFuturesOptionMaster(
    equityIndexFuturesOptionMasterUploadRequest: EquityIndexFuturesOptionMasterUploadRequest
  )

  fun uploadCurrencyMaster(currencyMasterUploadRequest: CurrencyMasterUploadRequest)

  fun downloadEquityMaster(): String

  fun downloadEquityIndexFuturesMaster(): String

  fun downloadEquityIndexFuturesOptionMaster(): String

  fun downloadCurrencyMaster(): String
}

class ProductMasterApiV1Resource
@Inject
constructor(private val productMasterManagementService: ProductMasterManagementService) :
  ProductMasterApiResource {
  override fun uploadEquityMaster(equityMasterUploadRequest: EquityMasterUploadRequest) {
    runCatching {
        runBlocking {
          equityMasterUploadRequest.multiPartData.forEachPart { part ->
            if (part is PartData.FileItem) {
              productMasterManagementService.uploadProductMaster(EquityMasterUploadParam(part))
            }
            part.dispose()
          }
        }
      }
      .onFailure { ex ->
        ex.printStackTrace()
        throw ex
      }
  }

  override fun uploadEquityIndexFuturesMaster(
    equityIndexFuturesMasterUploadRequest: EquityIndexFuturesMasterUploadRequest
  ) {
    runCatching {
        runBlocking {
          equityIndexFuturesMasterUploadRequest.multiPartData.forEachPart { part ->
            if (part is PartData.FileItem) {
              productMasterManagementService.uploadProductMaster(
                EquityIndexFuturesMasterUploadParam(part)
              )
            }
            part.dispose()
          }
        }
      }
      .exceptionOrNull()
  }

  override fun uploadEquityIndexFuturesOptionMaster(
    equityIndexFuturesOptionMasterUploadRequest: EquityIndexFuturesOptionMasterUploadRequest
  ) {
    runCatching {
        runBlocking {
          equityIndexFuturesOptionMasterUploadRequest.multiPartData.forEachPart { part ->
            if (part is PartData.FileItem) {
              productMasterManagementService.uploadProductMaster(
                EquityIndexFuturesOptionMasterUploadParam(part)
              )
            }
            part.dispose()
          }
        }
      }
      .onFailure { ex -> throw ex }
  }

  override fun uploadCurrencyMaster(currencyMasterUploadRequest: CurrencyMasterUploadRequest) {
    runCatching {
        runBlocking {
          currencyMasterUploadRequest.multiPartData.forEachPart { part ->
            if (part is PartData.FileItem) {
              productMasterManagementService.uploadProductMaster(CurrencyMasterUploadParam(part))
            }
            part.dispose()
          }
        }
      }
      .onFailure { ex -> throw ex }
  }

  override fun downloadEquityMaster(): String {
    return productMasterManagementService.downloadProductMaster(EquityMasterDownloadParam(""))
  }

  override fun downloadEquityIndexFuturesMaster(): String {
    return productMasterManagementService.downloadProductMaster(
      EquityIndexFuturesMasterDownloadParam("")
    )
  }

  override fun downloadEquityIndexFuturesOptionMaster(): String {
    return productMasterManagementService.downloadProductMaster(
      EquityIndexFuturesOptionMasterDownloadParam("")
    )
  }

  override fun downloadCurrencyMaster(): String {
    return productMasterManagementService.downloadProductMaster(CurrencyMasterDownloadParam(""))
  }
}
