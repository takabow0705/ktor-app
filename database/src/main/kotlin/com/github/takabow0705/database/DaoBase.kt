package com.github.takabow0705.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface DaoBase {
  suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) {
      runCatching { block() }
        .onFailure { ex ->
          ex.printStackTrace()
          throw ex
        }
        .getOrThrow()
    }
}
