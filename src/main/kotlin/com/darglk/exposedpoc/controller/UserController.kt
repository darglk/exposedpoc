package com.darglk.exposedpoc.controller

import com.darglk.exposedpoc.exception.ValidationException
import com.darglk.exposedpoc.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import kotlin.math.absoluteValue

@RestController
@RequestMapping("/api/activejdbc/users")
class UserController(private val userService: UserService) {
    
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: String): UserResponse {
        return userService.getUser(id)
    }
    
    @GetMapping("/{id}/dsl")
    fun getUserDsl(@PathVariable("id") id: String): UserResponse {
        return userService.getUserDsl(id)
    }
    
    @PostMapping("/signup")
    fun signup(
        @RequestBody request: CreateUserRequest,
        errors: Errors
    ): ResponseEntity<*> {
        if (errors.hasErrors()) {
            throw ValidationException(errors)
        }
        val response = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body<Any>(response)
    }
    
    @PostMapping("/signup/dsl")
    fun signupDsl(
        @RequestBody request: CreateUserRequest,
        errors: Errors
    ) {
        if (errors.hasErrors()) {
            throw ValidationException(errors)
        }
        userService.createUserDsl(request)
    }
    
    @GetMapping("/users")
    fun getUsers(
        @RequestParam("search", required = false) search: String?,
        @RequestParam("page", defaultValue = "0", required = false) page: Int, @RequestParam("pageSize", defaultValue = "1", required = false) pageSize: Int
    ): List<UsersResponse> {
        return userService.getUsers(search, page.absoluteValue, pageSize.absoluteValue)
    }
    
    @GetMapping("/users/dsl")
    fun getUsersDsl(
        @RequestParam("search", required = false) search: String?,
        @RequestParam("page", defaultValue = "0", required = false) page: Int, @RequestParam("pageSize", defaultValue = "1", required = false) pageSize: Int
    ): List<UsersResponse> {
        return userService.getUsersDsl(search, page.absoluteValue, pageSize.absoluteValue)
    }
    
    @GetMapping("/exist")
    fun doesUserExist(@RequestParam("email") email: String): Boolean {
        return userService.doesUserExist(email)
    }
    
    @GetMapping("/exist/dsl")
    fun doesUserExistDsl(@RequestParam("email") email: String): Boolean {
        return userService.doesUserExistDsl(email)
    }
    
    @PutMapping("/password")
    fun updatePassword(@RequestBody request: UpdatePasswordRequest) {
        userService.updatePassword(request)
    }
    
    @PutMapping("/password/dsl")
    fun updatePasswordDsl(@RequestBody request: UpdatePasswordRequest) {
        userService.updatePasswordDsl(request)
    }
    
    @PutMapping("/email")
    fun updateEmail(@RequestBody request: UpdateEmailRequest) {
        userService.updateEmail(request)
    }
    
    @PutMapping("/email/dsl")
    fun updateEmailDsl(@RequestBody request: UpdateEmailRequest) {
        userService.updateEmailDsl(request)
    }
}