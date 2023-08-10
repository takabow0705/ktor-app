package com.github.takabow0705.database.util

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> transactionWrapper(statement: Transaction.() -> T): T {
  return transaction {
    runCatching { statement() }
      .onFailure { ex ->
        rollback()
        throw ex
      }
      .getOrThrow()
  }
}
