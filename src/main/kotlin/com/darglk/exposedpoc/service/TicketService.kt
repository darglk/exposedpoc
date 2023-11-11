package com.darglk.exposedpoc.service

import com.darglk.exposedpoc.controller.CreateTicketRequest
import com.darglk.exposedpoc.controller.TicketResponse

interface TicketService {
    fun createTicket(request: CreateTicketRequest)
    fun getTickets(): List<TicketResponse>
    fun deleteTicket(ticketId: String)
    fun createTicketDsl(request: CreateTicketRequest)
    fun getTicketsDsl(): List<TicketResponse>
    fun deleteTicketDsl(ticketId: String)
}