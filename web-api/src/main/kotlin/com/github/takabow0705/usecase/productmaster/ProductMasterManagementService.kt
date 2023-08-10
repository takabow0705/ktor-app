package com.github.takabow0705.usecase.productmaster

import com.github.takabow0705.database.util.transactionWrapper
import com.github.takabow0705.domain.productmaster.*
import com.github.takabow0705.infrastructure.productmaster.CurrencyRepository
import com.github.takabow0705.infrastructure.productmaster.EquityIndexFuturesOptionRepository
import com.github.takabow0705.infrastructure.productmaster.EquityIndexFuturesRepository
import com.github.takabow0705.infrastructure.productmaster.EquityRepository
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

interface ProductMasterManagementService {

  fun uploadProductMaster(uploadParam: ProductMasterUploadParam)

  fun downloadProductMaster(downloadParam: ProductMasterDownloadParam): String
}

class ProductMasterManagementServiceImpl
@Inject
constructor(
  private val equityRepository: EquityRepository,
  private val equityIndexFuturesRepository: EquityIndexFuturesRepository,
  private val equityIndexFuturesOptionRepository: EquityIndexFuturesOptionRepository,
  private val currencyRepository: CurrencyRepository
) : ProductMasterManagementService {
  private val logger = LoggerFactory.getLogger(javaClass)

  override fun uploadProductMaster(uploadParam: ProductMasterUploadParam) {
    transactionWrapper {
      runCatching {
          runBlocking {
            when (uploadParam) {
              is EquityMasterUploadParam -> {
                equityRepository.deleteAll()
                equityRepository.bulkInsert(EquityMasterCsvParser.parse(uploadParam.csv))
              }
              is EquityIndexFuturesMasterUploadParam -> {
                equityIndexFuturesRepository.deleteAll()
                equityIndexFuturesRepository.bulkInsert(
                  EquityIndexFuturesMasterCsvParser.parse(uploadParam.csv)
                )
              }
              is EquityIndexFuturesOptionMasterUploadParam -> {
                equityIndexFuturesOptionRepository.deleteAll()
                equityIndexFuturesOptionRepository.bulkInsert(
                  EquityIndexFuturesOptionMasterCsvParser.parse(uploadParam.csv)
                )
              }
              is CurrencyMasterUploadParam -> {
                currencyRepository.deleteAll()
                currencyRepository.bulkInsert(CurrencyMasterCsvParser.parse(uploadParam.csv))
              }
            }
          }
        }
        .onFailure { ex ->
          logger.error("ProductMaster Registration is Failed. Cause {}", ex.message)
          throw ex
        }
        .onSuccess {
          logger.info("ProductMaster Registration is Success. type {}", uploadParam.javaClass)
        }
        .getOrThrow()
    }
  }

  override fun downloadProductMaster(downloadParam: ProductMasterDownloadParam): String {
    return runCatching {
        transactionWrapper {
          runBlocking {
            when (downloadParam) {
              is EquityMasterDownloadParam -> {
                EquityMasterCsvParser.createCsv(equityRepository.findAll())
              }
              is EquityIndexFuturesMasterDownloadParam -> {
                EquityIndexFuturesMasterCsvParser.createCsv(equityIndexFuturesRepository.findAll())
              }
              is EquityIndexFuturesOptionMasterDownloadParam -> {
                EquityIndexFuturesOptionMasterCsvParser.createCsv(
                  equityIndexFuturesOptionRepository.findAll()
                )
              }
              is CurrencyMasterDownloadParam -> {
                CurrencyMasterCsvParser.createCsv(currencyRepository.findAll())
              }
            }
          }
        }
      }
      .onFailure { ex -> logger.error("Download　ProductMaster is failed. Cause: {}", ex.message) }
      .getOrThrow()
  }
}
