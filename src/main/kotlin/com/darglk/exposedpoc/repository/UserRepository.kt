package com.darglk.exposedpoc.repository

import com.darglk.exposedpoc.controller.CreateUserRequest

interface UserRepository {
    fun findUser(id: String): User?
    fun findUserDsl(id: String): User?
    fun findUserByEmail(email: String): User?
    fun findUserByEmailDsl(email: String): User?
    fun insertUser(user: CreateUserRequest): User
    fun insertUserDsl(user: CreateUserRequest)
    fun doesEmailExist(email: String): Boolean
    fun doesEmailExistDsl(email: String): Boolean
    fun updatePassword(userId: String, password: String)
    fun updatePasswordDsl(userId: String, password: String)
    fun updateEmail(userId: String, email: String)
    fun updateEmailDsl(userId: String, email: String)
    fun getUsers(search: String?, page: Int, pageSize: Int): List<User>
    fun getUsersDsl(search: String?, page: Int, pageSize: Int): List<User>
}