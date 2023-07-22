package com.github.takabow0705.infrastructure

import com.github.takabow0705.database.user.UserDao
import com.github.takabow0705.domain.user.User
import javax.inject.Inject

interface UserRepository {
  suspend fun findAll(): List<User>

  suspend fun findOne(emailAddress: String): User?

  suspend fun createUser(user: User): User?

  suspend fun updateUser(user: User): Boolean
}

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
  override suspend fun findAll(): List<User> {
    return userDao.findAll().map { u -> User.map(u) }
  }

  override suspend fun findOne(emailAddress: String): User? {
    val data = userDao.findOne(emailAddress) ?: return null
    return User.map(data)
  }

  override suspend fun createUser(user: User): User? {
    val data = userDao.createUser(user.toTable()) ?: return null
    return User.map(data)
  }

  override suspend fun updateUser(user: User): Boolean {
    return userDao.updateUser(user.toTable())
  }
}
