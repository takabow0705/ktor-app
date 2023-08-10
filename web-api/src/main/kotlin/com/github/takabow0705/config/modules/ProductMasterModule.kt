package com.github.takabow0705.config.modules

import com.github.takabow0705.database.product.*
import com.github.takabow0705.infrastructure.productmaster.*
import com.github.takabow0705.presentation.productmaster.ProductMasterApiResource
import com.github.takabow0705.presentation.productmaster.ProductMasterApiV1Resource
import com.github.takabow0705.usecase.productmaster.ProductMasterManagementService
import com.github.takabow0705.usecase.productmaster.ProductMasterManagementServiceImpl
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

  @Provides
  fun provideEquityRepository(equityDao: EquityDao): EquityRepository {
    return EquityRepositoryImpl(equityDao)
  }

  @Provides
  fun provideEquityIndexFuturesRepository(
    equityIndexFuturesDao: EquityIndexFuturesDao
  ): EquityIndexFuturesRepository {
    return EquityIndexFuturesRepositoryImpl(equityIndexFuturesDao)
  }

  @Provides
  fun provideEquityIndexFuturesOpionRepository(
    equityIndexFuturesOptionsDao: EquityIndexFuturesOptionDao
  ): EquityIndexFuturesOptionRepository {
    return EquityIndexFuturesOptionRepositoryImpl(equityIndexFuturesOptionsDao)
  }

  @Provides
  fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
    return CurrencyRepositoryImpl(currencyDao)
  }

  @Provides
  fun provideProductMasterManagementService(
    equityRepository: EquityRepository,
    equityIndexFuturesRepository: EquityIndexFuturesRepository,
    equityIndexFuturesOptionRepository: EquityIndexFuturesOptionRepository,
    currencyRepository: CurrencyRepository
  ): ProductMasterManagementService {
    return ProductMasterManagementServiceImpl(
      equityRepository,
      equityIndexFuturesRepository,
      equityIndexFuturesOptionRepository,
      currencyRepository
    )
  }

  @Provides
  fun provideApiResource(
    productMasterManagementService: ProductMasterManagementService
  ): ProductMasterApiResource {
    return ProductMasterApiV1Resource(productMasterManagementService)
  }
}
