package com.darglk.exposedpoc.controller

import com.darglk.exposedpoc.controller.AttachmentRequest

data class CreateTicketRequest(
    val title: String,
    val description: String,
    val userId: String,
    val attachments: List<AttachmentRequest>
)
