package com.github.takabow0705.infrastructure.productmaster

import com.github.takabow0705.database.product.EquityIndexFuturesOptionDao
import com.github.takabow0705.domain.productmaster.EquityIndexFuturesOption
import javax.inject.Inject

interface EquityIndexFuturesOptionRepository {
  suspend fun findAll(): List<EquityIndexFuturesOption>

  suspend fun bulkInsert(target: List<EquityIndexFuturesOption>): List<EquityIndexFuturesOption>

  suspend fun deleteAll(): Boolean
}

class EquityIndexFuturesOptionRepositoryImpl
@Inject
constructor(private val equityIndexFuturesOptionDao: EquityIndexFuturesOptionDao) :
  EquityIndexFuturesOptionRepository {
  override suspend fun findAll(): List<EquityIndexFuturesOption> {
    return equityIndexFuturesOptionDao.findAll().map(EquityIndexFuturesOption::map)
  }

  override suspend fun bulkInsert(
    target: List<EquityIndexFuturesOption>
  ): List<EquityIndexFuturesOption> {
    return equityIndexFuturesOptionDao
      .bulkInsert(target.map(EquityIndexFuturesOption::toTable))
      .map(EquityIndexFuturesOption::map)
  }

  override suspend fun deleteAll(): Boolean {
    return equityIndexFuturesOptionDao.deleteAll()
  }
}
