package com.github.takabow0705.config

import com.github.takabow0705.config.modules.UserModule
import com.github.takabow0705.presentation.user.UserApiResource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UserModule::class])
interface ApiResourceComponent {
  fun userApiResource(): UserApiResource
}
