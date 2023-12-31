package com.github.takabow0705.database.product

import java.time.LocalDate
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

data class EquityTable(
  val productCode: String,
  val productName: String,
  val exchange: String,
  val currency: String,
  val listedDate: LocalDate
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EquityTable

    return productCode == other.productCode
  }

  override fun hashCode(): Int {
    return productCode.hashCode()
  }
}

data class EquityIndexFutureTable(
  val productCode: String,
  val productName: String,
  val exchange: String,
  val currency: String,
  val listedDate: LocalDate
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EquityIndexFutureTable

    return productCode == other.productCode
  }

  override fun hashCode(): Int {
    return productCode.hashCode()
  }
}

data class EquityIndexFuturesOptionTable(
  val productCode: String,
  val underlyingProductCode: String,
  val productName: String,
  val exchange: String,
  val currency: String,
  val listedDate: LocalDate
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EquityIndexFuturesOptionTable

    return productCode == other.productCode
  }

  override fun hashCode(): Int {

    return productCode.hashCode()
  }
}

data class CurrencyTable(
  val currencyCode: String,
  val countryCode: String,
  val currencyName: String
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as CurrencyTable

    return currencyCode == other.currencyCode
  }

  override fun hashCode(): Int {
    return currencyCode.hashCode()
  }
}

object EquityMaster : Table("equity") {
  val productCode = varchar("product_code", 100)
  val productName = varchar("product_name", 500)
  val exchange = varchar("exchange", 10)
  val currency = char("currency", 3)
  val listedDate = date("listed_date")

  override val primaryKey = PrimaryKey(productCode)
}

object EquityIndexFuturesMaster : Table("equity_index_futures") {
  val productCode = varchar("product_code", 100)
  val productName = varchar("product_name", 500)
  val exchange = varchar("exchange", 10)
  val currency = char("currency", 3)
  val listedDate = date("listed_date")

  override val primaryKey = PrimaryKey(productCode)
}

object EquityIndexFuturesOptionMaster : Table("equity_index_futures_option") {
  val productCode = varchar("product_code", 100)
  val underlyingProductCode = varchar("underlying_product_code", 100)
  val productName = varchar("product_name", 500)
  val exchange = varchar("exchange", 10)
  val currency = char("currency", 3)
  val listedDate = date("listed_date")

  override val primaryKey = PrimaryKey(productCode)
}

object CurrencyMaster : Table("currency") {
  val currencyCode = char("currency_code", 3)
  val countryCode = char("country_code", 10)
  val currencyName = varchar("currency_name", 100)

  override val primaryKey = PrimaryKey(currencyCode)
}
