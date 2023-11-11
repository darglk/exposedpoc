package com.darglk.exposedpoc.controller

import com.darglk.exposedpoc.exception.ValidationException
import com.darglk.exposedpoc.service.TicketService
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activejdbc/tickets")
class TicketController(private val ticketService: TicketService) {
    
    @PostMapping
    fun createTicket(@RequestBody request: CreateTicketRequest, errors: Errors) {
        if (errors.hasErrors()) {
            throw ValidationException(errors)
        }
        ticketService.createTicket(request)
    }
    
    @PostMapping("/dsl")
    fun createTicketDsl(@RequestBody request: CreateTicketRequest, errors: Errors) {
        if (errors.hasErrors()) {
            throw ValidationException(errors)
        }
        ticketService.createTicketDsl(request)
    }
    
    @GetMapping
    fun getTickets() : List<TicketResponse> {
        return ticketService.getTickets()
    }
    
    @GetMapping("/dsl")
    fun getTicketsDsl() : List<TicketResponse> {
        return ticketService.getTicketsDsl()
    }
    
    @DeleteMapping("/{ticketId}")
    fun deleteTicket(@PathVariable("ticketId") ticketId: String) {
        ticketService.deleteTicket(ticketId)
    }
    
    @DeleteMapping("/{ticketId}/dsl")
    fun deleteTicketDsl(@PathVariable("ticketId") ticketId: String) {
        ticketService.deleteTicketDsl(ticketId)
    }
}