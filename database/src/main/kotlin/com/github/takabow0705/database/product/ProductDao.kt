package com.github.takabow0705.database.product

import com.github.takabow0705.database.DaoBase
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll

interface EquityDao : DaoBase {
  suspend fun findAll(): List<EquityTable>

  suspend fun bulkInsert(target: List<EquityTable>): List<EquityTable>

  suspend fun deleteAll(): Boolean
}

interface EquityIndexFuturesDao : DaoBase {
  suspend fun findAll(): List<EquityIndexFutureTable>

  suspend fun bulkInsert(target: List<EquityIndexFutureTable>): List<EquityIndexFutureTable>

  suspend fun deleteAll(): Boolean
}

interface EquityIndexFuturesOptionDao : DaoBase {
  suspend fun findAll(): List<EquityIndexFuturesOptionTable>

  suspend fun bulkInsert(
    target: List<EquityIndexFuturesOptionTable>
  ): List<EquityIndexFuturesOptionTable>

  suspend fun deleteAll(): Boolean
}

interface CurrencyDao : DaoBase {
  suspend fun findAll(): List<CurrencyTable>

  suspend fun bulkInsert(target: List<CurrencyTable>): List<CurrencyTable>

  suspend fun deleteAll(): Boolean
}

class EquityDaoImpl : EquityDao {
  override suspend fun findAll(): List<EquityTable> {
    return dbQuery { EquityMaster.selectAll().map(::mapToEquity).toList() }
  }

  override suspend fun bulkInsert(target: List<EquityTable>): List<EquityTable> {
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

  private fun mapToEquity(row: ResultRow): EquityTable =
    EquityTable(
      productCode = row[EquityMaster.productCode],
      productName = row[EquityMaster.productName],
      exchange = row[EquityMaster.exchange],
      currency = row[EquityMaster.currency],
      listedDate = row[EquityMaster.listedDate]
    )
}

class EquityIndexFuturesDaoImpl : EquityIndexFuturesDao {
  override suspend fun findAll(): List<EquityIndexFutureTable> {
    return dbQuery { EquityIndexFuturesMaster.selectAll().map(::mapToEquityIndexFutures).toList() }
  }

  override suspend fun bulkInsert(
    target: List<EquityIndexFutureTable>
  ): List<EquityIndexFutureTable> {
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

  private fun mapToEquityIndexFutures(row: ResultRow): EquityIndexFutureTable =
    EquityIndexFutureTable(
      productCode = row[EquityIndexFuturesMaster.productCode],
      productName = row[EquityIndexFuturesMaster.productName],
      exchange = row[EquityIndexFuturesMaster.exchange],
      currency = row[EquityIndexFuturesMaster.currency],
      listedDate = row[EquityIndexFuturesMaster.listedDate]
    )
}

class EquityIndexFuturesOptionDaoImpl : EquityIndexFuturesOptionDao {
  override suspend fun findAll(): List<EquityIndexFuturesOptionTable> {
    return dbQuery {
      EquityIndexFuturesOptionMaster.selectAll().map(::mapToEquityIndexFuturesOption).toList()
    }
  }

  override suspend fun bulkInsert(
    target: List<EquityIndexFuturesOptionTable>
  ): List<EquityIndexFuturesOptionTable> {
    return dbQuery {
      EquityIndexFuturesOptionMaster.batchInsert(target) { t ->
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

  private fun mapToEquityIndexFuturesOption(row: ResultRow): EquityIndexFuturesOptionTable =
    EquityIndexFuturesOptionTable(
      productCode = row[EquityIndexFuturesOptionMaster.productCode],
      productName = row[EquityIndexFuturesOptionMaster.productName],
      underlyingProductCode = row[EquityIndexFuturesOptionMaster.underlyingProductCode],
      exchange = row[EquityIndexFuturesOptionMaster.exchange],
      currency = row[EquityIndexFuturesOptionMaster.currency],
      listedDate = row[EquityIndexFuturesOptionMaster.listedDate]
    )
}

class CurrencyDaoImpl : CurrencyDao {
  override suspend fun findAll(): List<CurrencyTable> {
    return dbQuery { CurrencyMaster.selectAll().map(::mapToCurrency).toList() }
  }

  override suspend fun bulkInsert(target: List<CurrencyTable>): List<CurrencyTable> {
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

  private fun mapToCurrency(row: ResultRow): CurrencyTable =
    CurrencyTable(
      currencyCode = row[CurrencyMaster.currencyCode],
      currencyName = row[CurrencyMaster.currencyName],
      countryCode = row[CurrencyMaster.countryCode]
    )
}
