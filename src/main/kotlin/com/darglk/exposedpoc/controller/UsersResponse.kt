package com.darglk.exposedpoc.controller

data class UsersResponse(
    val id: String,
    val email: String,
    val authorities: List<AuthorityResponse>
)
