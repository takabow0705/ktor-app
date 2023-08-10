package com.github.takabow0705.database

interface DaoBase {
  suspend fun <T> dbQuery(block: suspend () -> T): T =
    runCatching { block() }
      .onFailure { ex ->
        ex.printStackTrace()
        throw ex
      }
      .getOrThrow()
}
