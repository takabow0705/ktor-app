package com.github.takabow0705.infrastructure.productmaster

import com.github.takabow0705.database.product.EquityIndexFuturesDao
import com.github.takabow0705.domain.productmaster.EquityIndexFuture
import javax.inject.Inject

interface EquityIndexFuturesRepository {
  suspend fun findAll(): List<EquityIndexFuture>

  suspend fun bulkInsert(target: List<EquityIndexFuture>): List<EquityIndexFuture>

  suspend fun deleteAll(): Boolean
}

class EquityIndexFuturesRepositoryImpl
@Inject
constructor(private val equityIndexFuturesDao: EquityIndexFuturesDao) :
  EquityIndexFuturesRepository {
  override suspend fun findAll(): List<EquityIndexFuture> {
    return equityIndexFuturesDao.findAll().map(EquityIndexFuture::map)
  }

  override suspend fun bulkInsert(target: List<EquityIndexFuture>): List<EquityIndexFuture> {
    return equityIndexFuturesDao
      .bulkInsert(target.map(EquityIndexFuture::toTable))
      .map(EquityIndexFuture::map)
  }

  override suspend fun deleteAll(): Boolean {
    return equityIndexFuturesDao.deleteAll()
  }
}
