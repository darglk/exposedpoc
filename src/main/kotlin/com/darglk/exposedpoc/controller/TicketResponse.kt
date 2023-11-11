package com.darglk.exposedpoc.controller

import com.darglk.exposedpoc.controller.AttachmentResponse
import java.time.Instant

data class TicketResponse(
    val id: String,
    val userId: String,
    val description: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val attachments: List<AttachmentResponse>
)
