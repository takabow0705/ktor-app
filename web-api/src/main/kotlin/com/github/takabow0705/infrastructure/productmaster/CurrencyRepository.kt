package com.github.takabow0705.infrastructure.productmaster

import com.github.takabow0705.database.product.CurrencyDao
import com.github.takabow0705.domain.productmaster.*
import javax.inject.Inject

interface CurrencyRepository {
  suspend fun findAll(): List<Currency>

  suspend fun bulkInsert(target: List<Currency>): List<Currency>

  suspend fun deleteAll(): Boolean
}

class CurrencyRepositoryImpl @Inject constructor(private val currencyDao: CurrencyDao) :
  CurrencyRepository {
  override suspend fun findAll(): List<Currency> {
    return currencyDao.findAll().map(Currency::map)
  }

  override suspend fun bulkInsert(target: List<Currency>): List<Currency> {
    return currencyDao.bulkInsert(target.map(Currency::toTable)).map(Currency::map)
  }

  override suspend fun deleteAll(): Boolean {
    return currencyDao.deleteAll()
  }
}
