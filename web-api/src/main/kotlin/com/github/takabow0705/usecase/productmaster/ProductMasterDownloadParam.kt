package com.github.takabow0705.usecase.productmaster

sealed class ProductMasterDownloadParam(fileName: String) {}

data class EquityMasterDownloadParam(val fileName: String) : ProductMasterDownloadParam(fileName)

data class EquityIndexFuturesMasterDownloadParam(val fileName: String) :
  ProductMasterDownloadParam(fileName)

data class EquityIndexFuturesOptionMasterDownloadParam(val fileName: String) :
  ProductMasterDownloadParam(fileName)

data class CurrencyMasterDownloadParam(val fileName: String) : ProductMasterDownloadParam(fileName)
