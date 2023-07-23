package com.github.takabow0705.infrastructure.productmaster

import com.github.takabow0705.domain.productmaster.*

interface CurrencyRepository {
  suspend fun findAll(): List<Currency>?

  suspend fun bulkInsert(target: List<Currency>): List<Currency>

  suspend fun deleteAll(): Boolean
}
