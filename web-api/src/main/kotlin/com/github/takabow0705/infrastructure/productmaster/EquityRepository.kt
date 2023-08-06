package com.github.takabow0705.infrastructure.productmaster

import com.github.takabow0705.database.product.EquityDao
import com.github.takabow0705.domain.productmaster.Equity
import javax.inject.Inject

interface EquityRepository {
  suspend fun findAll(): List<Equity>

  suspend fun bulkInsert(target: List<Equity>): List<Equity>

  suspend fun deleteAll(): Boolean
}

class EquityRepositoryImpl @Inject constructor(private val equityDao: EquityDao) :
  EquityRepository {
  override suspend fun findAll(): List<Equity> {
    return equityDao.findAll().map(Equity::map)
  }

  override suspend fun bulkInsert(target: List<Equity>): List<Equity> {
    return equityDao.bulkInsert(target.map(Equity::toTable)).map(Equity::map)
  }

  override suspend fun deleteAll(): Boolean {
    return equityDao.deleteAll()
  }
}
