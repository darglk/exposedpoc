package com.darglk.exposedpoc.controller

data class CreateUserRequest(
    val email: String,
    val password:  String
)