package com.darglk.exposedpoc.controller

data class UpdatePasswordRequest(
    val userId: String,
    val password: String
)
