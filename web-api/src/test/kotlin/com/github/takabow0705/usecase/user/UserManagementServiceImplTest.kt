package com.github.takabow0705.usecase.user

import com.github.takabow0705.database.user.UserDao
import com.github.takabow0705.database.user.UserTable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserManagementServiceImplTest {

  @MockK lateinit var userRepository: UserDao
  @MockK lateinit var user: UserTable
  lateinit var userManagementService: UserManagementServiceImpl
  private val targetMailAddress = "test1@example.com"

  @BeforeTest
  fun setup() {
    userRepository = mockk<UserDao>()
    user = mockk<UserTable>()
    userManagementService = UserManagementServiceImpl(userRepository)
    MockKAnnotations.init(this)
  }

  @Test
  fun disableUserWhenTargetUserNotExist() {
    coEvery { userRepository.findOne(targetMailAddress) } returns null
    userManagementService = UserManagementServiceImpl(userRepository)

    userManagementService.disableUser(targetMailAddress)

    coVerify(exactly = 0) { userRepository.updateUser(any()) }
  }

  @Test
  fun disableUserWhenTargetUserExist() {
    coEvery { userRepository.findOne(targetMailAddress) } returns
      UserTable(1, "", "", "", false, "")
    coEvery { userRepository.updateUser(any()) } returns true
    every { user.disable() } returns UserTable(1, "", "", "", true, "")
    userManagementService = UserManagementServiceImpl(userRepository)

    userManagementService.disableUser(targetMailAddress)

    coVerify(exactly = 1) { userRepository.updateUser(any()) }
  }
}
