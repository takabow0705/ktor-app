package com.github.takabow0705.usecase.user

import com.github.takabow0705.database.util.transactionWrapper
import com.github.takabow0705.domain.user.User
import com.github.takabow0705.infrastructure.user.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

interface UserManagementService {
  fun register(user: User): User?

  fun disableUser(mailAddress: String)

  fun enableUser(mailAddress: String)

  fun list(): List<User>
}

class UserManagementServiceImpl @Inject constructor(private val userRepository: UserRepository) :
  UserManagementService {
  private val logger = LoggerFactory.getLogger(javaClass)

  /** 新規のユーザを登録 */
  override fun register(user: User): User? {
    return runCatching { transactionWrapper { runBlocking { userRepository.createUser(user) } } }
      .onSuccess { logger.info("Registered new User.") }
      .onFailure { e -> logger.warn("User registration is failed. Caused by {}", e.message) }
      .getOrThrow()
  }

  /** 指定されたユーザを無効化する */
  override fun disableUser(mailAddress: String) {
    logger.info("Disable User : $mailAddress")
    val user =
      runCatching { transactionWrapper { runBlocking { userRepository.findOne(mailAddress) } } }
        .onFailure { e -> logger.warn("Unexpected Error occurred. {}", e.printStackTrace()) }
        .getOrNull()
        ?: run {
          logger.warn("Target User is not Found")
          return
        }
    runBlocking { userRepository.updateUser(user.disable()) }
  }

  /** 指定されたユーザを有効化する */
  override fun enableUser(mailAddress: String) {
    logger.info("Activate User : $mailAddress")
    val user =
      runCatching { transactionWrapper { runBlocking { userRepository.findOne(mailAddress) } } }
        .onFailure { e -> logger.warn("Unexpected Error occurred. {}", e.printStackTrace()) }
        .getOrNull()
        ?: run {
          logger.warn("Target User is not Found")
          return
        }
    runBlocking { userRepository.updateUser(user.enable()) }
  }

  /** ユーザ全量を取得する */
  override fun list(): List<User> {
    return transactionWrapper { runBlocking { userRepository.findAll() } }
  }
}
