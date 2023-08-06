package com.github.takabow0705.config.modules

import com.github.takabow0705.database.product.*
import dagger.Module
import dagger.Provides

@Module
class ProductMasterModule {
  @Provides
  fun provideEquityDao(): EquityDao {
    return EquityDaoImpl()
  }

  @Provides
  fun provideEquityIndexFuturesDao(): EquityIndexFuturesDao {
    return EquityIndexFuturesDaoImpl()
  }

  @Provides
  fun provideEquityIndexFuturesOptionDao(): EquityIndexFuturesOptionDao {
    return EquityIndexFuturesOptionDaoImpl()
  }

  @Provides
  fun provideCurrencyDao(): CurrencyDao {
    return CurrencyDaoImpl()
  }
}
