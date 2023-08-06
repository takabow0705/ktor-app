package com.github.takabow0705

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
  private val yyyyMMddFormat = DateTimeFormatter.ofPattern("yyyyMMdd")

  /** yyyy-MM-dd形式の日付文字列をLocalDate型に変換して返します。 */
  fun convertToLocalDate(yyyyMMdd: String): LocalDate {
    return LocalDate.parse(yyyyMMdd, yyyyMMddFormat)
  }
}
