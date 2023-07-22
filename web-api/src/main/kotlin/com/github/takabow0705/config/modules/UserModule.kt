package com.github.takabow0705.config.modules

import com.github.takabow0705.database.user.UserRepository
import com.github.takabow0705.database.user.UserRepositoryImpl
import com.github.takabow0705.presentation.user.UserApiResource
import com.github.takabow0705.presentation.user.UserApiV1Resource
import com.github.takabow0705.usecase.user.UserManagementService
import com.github.takabow0705.usecase.user.UserManagementServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {
  @Provides
  fun provideUserRepository(): UserRepository {
    return UserRepositoryImpl()
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
