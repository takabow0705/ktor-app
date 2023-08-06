package com.github.takabow0705.domain.productmaster

import com.github.takabow0705.database.product.CurrencyTable
import com.github.takabow0705.database.product.EquityIndexFutureTable
import com.github.takabow0705.database.product.EquityIndexFuturesOptionTable
import com.github.takabow0705.database.product.EquityTable
import io.ktor.util.reflect.*
import java.time.LocalDate

/** 銘柄マスタを表すインターフェース */
sealed class ProductMaster

data class Equity(
  val productCode: String,
  val productName: String,
  val exchange: String,
  val currency: String,
  val listedDate: LocalDate
) : ProductMaster() {
  companion object {
    fun map(equityTable: EquityTable): Equity {
      return Equity(
        productCode = equityTable.productCode,
        productName = equityTable.productName,
        exchange = equityTable.exchange,
        currency = equityTable.currency,
        listedDate = equityTable.listedDate
      )
    }
  }

  fun toTable(): EquityTable {
    return EquityTable(
      this.productCode,
      this.productName,
      this.exchange,
      this.currency,
      this.listedDate
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Equity

    return productCode == other.productCode
  }

  override fun hashCode(): Int {
    return productCode.hashCode()
  }
}

data class EquityIndexFuture(
  val productCode: String,
  val productName: String,
  val exchange: String,
  val currency: String,
  val listedDate: LocalDate
) : ProductMaster() {
  companion object {
    fun map(equityIndexFuturesTable: EquityIndexFutureTable): EquityIndexFuture {
      return EquityIndexFuture(
        productCode = equityIndexFuturesTable.productCode,
        productName = equityIndexFuturesTable.productName,
        exchange = equityIndexFuturesTable.exchange,
        currency = equityIndexFuturesTable.currency,
        listedDate = equityIndexFuturesTable.listedDate
      )
    }
  }

  fun toTable(): EquityIndexFutureTable {
    return EquityIndexFutureTable(
      this.productCode,
      this.productName,
      this.exchange,
      this.currency,
      this.listedDate
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EquityIndexFuture

    return productCode == other.productCode
  }

  override fun hashCode(): Int {
    return productCode.hashCode()
  }
}

data class EquityIndexFuturesOption(
  val productCode: String,
  val underlyingProductCode: String,
  val productName: String,
  val exchange: String, /*TSE, NYSE, OSE, CME */
  val currency: String,
  val listedDate: LocalDate
) : ProductMaster() {
  companion object {
    fun map(d: EquityIndexFuturesOptionTable): EquityIndexFuturesOption {
      return EquityIndexFuturesOption(
        productCode = d.productCode,
        productName = d.productName,
        underlyingProductCode = d.underlyingProductCode,
        exchange = d.exchange,
        currency = d.currency,
        listedDate = d.listedDate
      )
    }
  }

  fun toTable(): EquityIndexFuturesOptionTable {
    return EquityIndexFuturesOptionTable(
      this.productCode,
      this.productName,
      this.underlyingProductCode,
      this.exchange,
      this.currency,
      this.listedDate
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EquityIndexFuturesOption

    return productCode == other.productCode
  }

  override fun hashCode(): Int {
    return productCode.hashCode()
  }
}

data class Currency(val currencyCode: String, val countryCode: String, val currencyName: String) :
  ProductMaster() {

  companion object {
    fun map(d: CurrencyTable): Currency {
      return Currency(
        currencyCode = d.currencyCode,
        currencyName = d.currencyName,
        countryCode = d.countryCode
      )
    }
  }

  fun toTable(): CurrencyTable {
    return CurrencyTable(this.currencyCode, this.countryCode, this.currencyName)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Currency

    return currencyCode == other.currencyCode
  }

  override fun hashCode(): Int {
    return currencyCode.hashCode()
  }
}
