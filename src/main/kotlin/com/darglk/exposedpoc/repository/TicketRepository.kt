package com.darglk.exposedpoc.repository

import com.darglk.exposedpoc.controller.CreateTicketRequest

interface TicketRepository {
    fun insert(ticket: CreateTicketRequest)
    fun insertDsl(ticket: CreateTicketRequest)
    fun select(): List<Ticket>
    fun selectDsl(): List<Ticket>
    fun delete(ticketId: String)
    fun deleteDsl(ticketId: String)
}