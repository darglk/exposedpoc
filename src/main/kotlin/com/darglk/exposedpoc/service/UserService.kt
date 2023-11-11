package com.darglk.exposedpoc.service

import com.darglk.exposedpoc.controller.*

interface UserService {
    fun getUser(id: String): UserResponse
    fun getUserDsl(id: String): UserResponse
    fun getUsers(search: String?, page: Int, pageSize: Int): List<UsersResponse>
    fun getUsersDsl(search: String?, page: Int, pageSize: Int): List<UsersResponse>
    fun createUser(createUserRequest: CreateUserRequest): CreateUserResponse
    fun createUserDsl(createUserRequest: CreateUserRequest)
    fun doesUserExist(email: String): Boolean
    fun doesUserExistDsl(email: String): Boolean
    fun updatePassword(request: UpdatePasswordRequest)
    fun updateEmail(request: UpdateEmailRequest)
    fun updateEmailDsl(request: UpdateEmailRequest)
    fun updatePasswordDsl(request: UpdatePasswordRequest)
}