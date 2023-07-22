package com.github.takabow0705.database.product

import com.github.takabow0705.database.RepositoryBase
import org.jetbrains.exposed.sql.*

interface EquityRepository : RepositoryBase {
  suspend fun findAll(): List<Equity>?

  suspend fun bulkInsert(target: List<Equity>): List<Equity>

  suspend fun deleteAll(): Boolean
}

interface EquityIndexFuturesRepository : RepositoryBase {
  suspend fun findAll(): List<EquityIndexFuture>?

  suspend fun bulkInsert(target: List<EquityIndexFuture>): List<EquityIndexFuture>

  suspend fun deleteAll(): Boolean
}

interface EquityIndexFuturesOptionRepository : RepositoryBase {
  suspend fun findAll(): List<EquityIndexFuturesOption>?

  suspend fun bulkInsert(target: List<EquityIndexFuturesOption>): List<EquityIndexFuturesOption>

  suspend fun deleteAll(): Boolean
}

interface CurrencyRepository : RepositoryBase {
  suspend fun findAll(): List<Currency>?

  suspend fun bulkInsert(target: List<Currency>): List<Currency>

  suspend fun deleteAll(): Boolean
}

class EquityRepositoryImpl : EquityRepository {
  override suspend fun findAll(): List<Equity>? {
    return dbQuery { EquityMaster.selectAll().map(::mapToEquity).toList() }
  }

  override suspend fun bulkInsert(target: List<Equity>): List<Equity> {
    return dbQuery {
      EquityMaster.batchInsert(target) { t ->
          this[EquityMaster.productCode] = t.productCode
          this[EquityMaster.productName] = t.productName
          this[EquityMaster.currency] = t.currency
          this[EquityMaster.exchange] = t.exchange
          this[EquityMaster.listedDate] = t.listedDate
        }
        .map(::mapToEquity)
    }
  }

  override suspend fun deleteAll(): Boolean {
    return dbQuery {
      // 実行に失敗した場合には0が返る
      EquityMaster.deleteAll() > 0
    }
  }

  private fun mapToEquity(row: ResultRow): Equity =
    Equity(
      productCode = row[EquityMaster.productCode],
      productName = row[EquityMaster.productName],
      exchange = row[EquityMaster.exchange],
      currency = row[EquityMaster.currency],
      listedDate = row[EquityMaster.listedDate]
    )
}

class EquityIndexFuturesRepositoryImpl : EquityIndexFuturesRepository {
  override suspend fun findAll(): List<EquityIndexFuture>? {
    return dbQuery { EquityMaster.selectAll().map(::mapToEquityIndexFutures).toList() }
  }

  override suspend fun bulkInsert(target: List<EquityIndexFuture>): List<EquityIndexFuture> {
    return dbQuery {
      EquityIndexFuturesMaster.batchInsert(target) { t ->
          this[EquityIndexFuturesMaster.productCode] = t.productCode
          this[EquityIndexFuturesMaster.productName] = t.productName
          this[EquityIndexFuturesMaster.currency] = t.currency
          this[EquityIndexFuturesMaster.exchange] = t.exchange
          this[EquityIndexFuturesMaster.listedDate] = t.listedDate
        }
        .map(::mapToEquityIndexFutures)
    }
  }

  override suspend fun deleteAll(): Boolean {
    return dbQuery {
      // 実行に失敗した場合には0が返る
      EquityIndexFuturesMaster.deleteAll() > 0
    }
  }

  private fun mapToEquityIndexFutures(row: ResultRow): EquityIndexFuture =
    EquityIndexFuture(
      productCode = row[EquityIndexFuturesMaster.productCode],
      productName = row[EquityIndexFuturesMaster.productName],
      exchange = row[EquityIndexFuturesMaster.exchange],
      currency = row[EquityIndexFuturesMaster.currency],
      listedDate = row[EquityIndexFuturesMaster.listedDate]
    )
}

class EquityIndexFuturesOptionRepositoryImpl : EquityIndexFuturesOptionRepository {
  override suspend fun findAll(): List<EquityIndexFuturesOption>? {
    return dbQuery {
      EquityIndexFuturesOptionMaster.selectAll().map(::mapToEquityIndexFuturesOption).toList()
    }
  }

  override suspend fun bulkInsert(
    target: List<EquityIndexFuturesOption>
  ): List<EquityIndexFuturesOption> {
    return dbQuery {
      EquityIndexFuturesMaster.batchInsert(target) { t ->
          this[EquityIndexFuturesOptionMaster.productCode] = t.productCode
          this[EquityIndexFuturesOptionMaster.productName] = t.productName
          this[EquityIndexFuturesOptionMaster.underlyingProductCode] = t.underlyingProductCode
          this[EquityIndexFuturesOptionMaster.currency] = t.currency
          this[EquityIndexFuturesOptionMaster.exchange] = t.exchange
          this[EquityIndexFuturesOptionMaster.listedDate] = t.listedDate
        }
        .map(::mapToEquityIndexFuturesOption)
    }
  }

  override suspend fun deleteAll(): Boolean {
    return dbQuery {
      // 実行に失敗した場合には0が返る
      EquityIndexFuturesOptionMaster.deleteAll() > 0
    }
  }

  private fun mapToEquityIndexFuturesOption(row: ResultRow): EquityIndexFuturesOption =
    EquityIndexFuturesOption(
      productCode = row[EquityIndexFuturesOptionMaster.productCode],
      productName = row[EquityIndexFuturesOptionMaster.productName],
      underlyingProductCode = row[EquityIndexFuturesOptionMaster.underlyingProductCode],
      exchange = row[EquityIndexFuturesOptionMaster.exchange],
      currency = row[EquityIndexFuturesOptionMaster.currency],
      listedDate = row[EquityIndexFuturesOptionMaster.listedDate]
    )
}

class CurrencyRepositoryImpl : CurrencyRepository {
  override suspend fun findAll(): List<Currency>? {
    return dbQuery { CurrencyMaster.selectAll().map(::mapToCurrency).toList() }
  }

  override suspend fun bulkInsert(target: List<Currency>): List<Currency> {
    return dbQuery {
      CurrencyMaster.batchInsert(target) { t ->
          this[CurrencyMaster.currencyCode] = t.currencyCode
          this[CurrencyMaster.currencyName] = t.currencyName
          this[CurrencyMaster.countryCode] = t.countryCode
        }
        .map(::mapToCurrency)
    }
  }

  override suspend fun deleteAll(): Boolean {
    return dbQuery {
      // 実行に失敗した場合には0が返る
      CurrencyMaster.deleteAll() > 0
    }
  }

  private fun mapToCurrency(row: ResultRow): Currency =
    Currency(
      currencyCode = row[CurrencyMaster.currencyCode],
      currencyName = row[CurrencyMaster.currencyName],
      countryCode = row[CurrencyMaster.countryCode]
    )
}
