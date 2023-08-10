package com.github.takabow0705.domain.productmaster

import com.github.takabow0705.DateUtils
import io.ktor.http.content.*
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter
import java.util.function.Function
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord

/** 画面からアップロードされたMultiPartFileとドメインオブジェクトの相互変換を担う */
sealed class ProductMasterCsvParser<T> {
  protected abstract val header: Array<String>
  protected abstract val fileName: String
  protected abstract val mapper: Function<CSVRecord, T>
  protected abstract val entityArrayMapper: Function<T, Array<String>>

  private val productMasterCsvFormat =
    CSVFormat.Builder.create(CSVFormat.DEFAULT).setHeader(*header)

  fun parse(
    inputStream: InputStream,
  ): List<T> {
    return run {
      productMasterCsvFormat
        .setSkipHeaderRecord(true)
        .build()
        .parse(InputStreamReader(inputStream))
        .map { csvRecord -> mapper.apply(csvRecord) }
    }
  }

  fun createCsv(productMaster: Iterable<T>): String {
    val csv = StringWriter()
    CSVPrinter(csv, productMasterCsvFormat.setSkipHeaderRecord(false).build()).use { csvPrinter ->
      productMaster
        .map { d -> entityArrayMapper.apply(d) }
        .forEach { arr -> csvPrinter.printRecord(*arr) }
    }
    print(csv)
    return csv.toString()
  }
}

// 株式銘柄マスタ用
object EquityMasterCsvParser : ProductMasterCsvParser<Equity>() {
  override val header: Array<String>
    get() = arrayOf("商品コード", "商品名", "取引所", "取引通貨", "上場日")

  override val fileName: String
    get() = "EquityMaster.csv"

  override val mapper: Function<CSVRecord, Equity>
    get() = Function { csvRecord ->
      Equity(
        productCode = csvRecord.get(header[0]),
        productName = csvRecord.get(header[1]),
        exchange = csvRecord.get(header[2]),
        currency = csvRecord.get(header[3]),
        listedDate = DateUtils.convertToLocalDate(csvRecord.get(header[4]))
      )
    }

  override val entityArrayMapper: Function<Equity, Array<String>>
    get() = Function { eq ->
      arrayOf(
        eq.productCode,
        eq.productName,
        eq.exchange,
        eq.currency,
        DateUtils.convertToString(eq.listedDate)
      )
    }

  fun parse(multiPartData: PartData.FileItem): List<Equity> {
    return multiPartData.streamProvider().use { input -> super.parse(input) }
  }
}

// 株価指数先物マスタ用
object EquityIndexFuturesMasterCsvParser : ProductMasterCsvParser<EquityIndexFuture>() {
  override val header: Array<String>
    get() = arrayOf("商品コード", "商品名", "取引所", "取引通貨", "上場日")

  override val fileName: String
    get() = "EquityIndexFuturesMaster.csv"

  override val mapper: Function<CSVRecord, EquityIndexFuture>
    get() = Function { csvRecord ->
      EquityIndexFuture(
        productCode = csvRecord.get(header[0]),
        productName = csvRecord.get(header[1]),
        exchange = csvRecord.get(header[2]),
        currency = csvRecord.get(header[3]),
        listedDate = DateUtils.convertToLocalDate(csvRecord.get(header[4]))
      )
    }

  override val entityArrayMapper: Function<EquityIndexFuture, Array<String>>
    get() = Function { eq ->
      arrayOf(
        eq.productCode,
        eq.productName,
        eq.exchange,
        eq.currency,
        DateUtils.convertToString(eq.listedDate)
      )
    }

  fun parse(multiPartData: PartData.FileItem): List<EquityIndexFuture> {
    return multiPartData.streamProvider().use { input -> super.parse(input) }
  }
}

// 株価指数先物オプションマスタ用
object EquityIndexFuturesOptionMasterCsvParser :
  ProductMasterCsvParser<EquityIndexFuturesOption>() {
  override val header: Array<String>
    get() = arrayOf("商品コード", "原資産商品コード", "商品名", "取引所", "取引通貨", "上場日")

  override val fileName: String
    get() = "EquityIndexFuturesOptionMaster.csv"

  override val mapper: Function<CSVRecord, EquityIndexFuturesOption>
    get() = Function { csvRecord ->
      EquityIndexFuturesOption(
        productCode = csvRecord.get(header[0]),
        underlyingProductCode = csvRecord.get(header[1]),
        productName = csvRecord.get(header[2]),
        exchange = csvRecord.get(header[3]),
        currency = csvRecord.get(header[4]),
        listedDate = DateUtils.convertToLocalDate(csvRecord.get(header[5]))
      )
    }

  override val entityArrayMapper: Function<EquityIndexFuturesOption, Array<String>>
    get() = Function { eq ->
      arrayOf(
        eq.productCode,
        eq.underlyingProductCode,
        eq.productName,
        eq.exchange,
        eq.currency,
        DateUtils.convertToString(eq.listedDate)
      )
    }

  fun parse(multiPartData: PartData.FileItem): List<EquityIndexFuturesOption> {
    return multiPartData.streamProvider().use { input ->
      super.parse(input) as List<EquityIndexFuturesOption>
    }
  }
}

// 通貨マスタ用
object CurrencyMasterCsvParser : ProductMasterCsvParser<Currency>() {
  override val header: Array<String>
    get() = arrayOf("通貨コード", "通貨名", "国コード")

  override val fileName: String
    get() = "CurrencyMaster.csv"

  override val mapper: Function<CSVRecord, Currency>
    get() = Function { csvRecord ->
      Currency(
        currencyCode = csvRecord.get(header[0]),
        currencyName = csvRecord.get(header[1]),
        countryCode = csvRecord.get(header[2])
      )
    }

  override val entityArrayMapper: Function<Currency, Array<String>>
    get() = Function { c -> arrayOf(c.currencyCode, c.currencyName, c.countryCode) }

  fun parse(multiPartData: PartData.FileItem): List<Currency> {
    return multiPartData.streamProvider().use { input -> super.parse(input) as List<Currency> }
  }
}
