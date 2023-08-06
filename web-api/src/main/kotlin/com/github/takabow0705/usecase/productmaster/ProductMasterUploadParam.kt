package com.github.takabow0705.usecase.productmaster

import io.ktor.http.content.*

sealed class ProductMasterUploadParam(csv: PartData.FileItem) {}

data class EquityMasterUploadParam(val csv: PartData.FileItem) : ProductMasterUploadParam(csv)

data class EquityIndexFuturesMasterUploadParam(val csv: PartData.FileItem) :
  ProductMasterUploadParam(csv)

data class EquityIndexFuturesOptionMasterUploadParam(val csv: PartData.FileItem) :
  ProductMasterUploadParam(csv)

data class CurrencyMasterUploadParam(val csv: PartData.FileItem) : ProductMasterUploadParam(csv)
