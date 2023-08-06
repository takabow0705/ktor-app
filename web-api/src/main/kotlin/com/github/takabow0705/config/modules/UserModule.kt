package com.github.takabow0705.config.modules

import com.github.takabow0705.database.user.UserDao
import com.github.takabow0705.database.user.UserDaoImpl
import com.github.takabow0705.infrastructure.user.UserRepository
import com.github.takabow0705.infrastructure.user.UserRepositoryImpl
import com.github.takabow0705.presentation.user.UserApiResource
import com.github.takabow0705.presentation.user.UserApiV1Resource
import com.github.takabow0705.usecase.user.UserManagementService
import com.github.takabow0705.usecase.user.UserManagementServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {
  @Provides
  fun provideUserDao(): UserDao {
    return UserDaoImpl()
  }

  @Provides
  fun provideUserRepository(userDao: UserDao): UserRepository {
    return UserRepositoryImpl(userDao)
  }

  @Provides
  fun provideUserManagementService(userRepository: UserRepository): UserManagementService {
    return UserManagementServiceImpl(userRepository)
  }

  @Provides
  fun provideUserApiResource(userManagementService: UserManagementService): UserApiResource {
    return UserApiV1Resource(userManagementService)
  }
}
