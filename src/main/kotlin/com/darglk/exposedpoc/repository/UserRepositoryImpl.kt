package com.darglk.exposedpoc.repository

import com.darglk.exposedpoc.controller.CreateUserRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl : UserRepository {
    override fun findUser(id: String): User? {
        return User.findById(UUID.fromString(id))
    }
    
    override fun findUserDsl(id: String): User? {
        return (Users leftJoin UsersAuthorities leftJoin Authorities)
            .selectAll()
            .limit(1)
            .withDistinct()
            .map { User.wrapRow(it) }
            .distinctBy { it.id }
            .firstOrNull()
    }
    
    override fun findUserByEmail(email: String): User? {
        return User.find { Users.email eq email }.singleOrNull()
    }
    
    override fun findUserByEmailDsl(email: String): User? {
        return Users.select(Users.email.eq(email)).limit(1, 0)
            .map { User.wrapRow(it) }.firstOrNull()
    }
    
    override fun insertUser(user: CreateUserRequest): User {
        val authority = Authority.all()
        return User.new(UUID.randomUUID()) {
            email = user.email
            password = user.password
            status = UserStatus.ENABLED.name
            authorities = authority
        }
    }
    
    override fun insertUserDsl(user: CreateUserRequest) {
        val userId = Users.insertAndGetId {
            it[email] = user.email
            it[password] = user.password
            it[status] = "CREATED"
        }
        
        Authorities.slice(Authorities.id).selectAll().forEach { a ->
            UsersAuthorities.insert {
                it[UsersAuthorities.user] = userId
                it[authority] = a[Authorities.id]
            }
        }
    }
    
    override fun doesEmailExist(email: String): Boolean {
        return findUserByEmail(email) != null
    }
    
    override fun doesEmailExistDsl(email: String): Boolean {
        return findUserByEmailDsl(email) != null
    }
    
    override fun updatePassword(userId: String, password: String) {
        User.findById(UUID.fromString(userId))?.let {
            it.password = password
        }
    }
    
    override fun updatePasswordDsl(userId: String, password: String) {
        Users.update({ Users.id eq UUID.fromString(userId) }) {
            it[Users.password] = password
        }
    }
    
    override fun updateEmail(userId: String, email: String) {
        User.findById(UUID.fromString(userId))?.let {
            it.email = email
        }
    }
    
    override fun updateEmailDsl(userId: String, email: String) {
        Users.update({ Users.id eq UUID.fromString(userId) }) {
            it[Users.email] = email
        }
    }
    
    override fun getUsers(search: String?, page: Int, pageSize: Int): List<User> {
        return if (search != null) {
            User.find {
                Users.email like search
            }.limit(pageSize, page.toLong()).toList()
        } else {
            User.all().limit(pageSize, page.toLong()).toList()
        }
    }
    
    override fun getUsersDsl(search: String?, page: Int, pageSize: Int): List<User> {
        val usersQuery = Users.selectAll()
        search?.let { usersQuery.andWhere { Users.email like it } }
        usersQuery.limit(pageSize, page.toLong())
        
        val query = (Users leftJoin UsersAuthorities leftJoin Authorities).selectAll()
        search?.let { query.andWhere { Users.email like it }  }
        return query.limit(pageSize, page.toLong())
            .withDistinct()
            .map { User.wrapRow(it) }
            .distinctBy { it.id }
    }
}