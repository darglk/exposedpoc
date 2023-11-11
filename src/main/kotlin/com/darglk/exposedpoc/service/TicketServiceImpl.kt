package com.darglk.exposedpoc.service

import com.darglk.exposedpoc.controller.AttachmentResponse
import com.darglk.exposedpoc.controller.CreateTicketRequest
import com.darglk.exposedpoc.controller.TicketResponse
import com.darglk.exposedpoc.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketServiceImpl(private val ticketRepository: TicketRepository) : TicketService {
    @Transactional
    override fun createTicket(request: CreateTicketRequest) {
        ticketRepository.insert(request)
    }
    @Transactional
    override fun getTickets(): List<TicketResponse> {
        return ticketRepository.select().map {
            TicketResponse(
                it.id.toString(), it.user.id.toString(), it.description, it.status, it.createdAt, it.updatedAt,
                it.attachments.map {
                    AttachmentResponse(
                        it.id.toString(), it.fileKey
                    )
                }
            )
        }
    }
    @Transactional
    override fun deleteTicket(ticketId: String) {
        ticketRepository.delete(ticketId)
    }
    
    @Transactional
    override fun createTicketDsl(request: CreateTicketRequest) {
        ticketRepository.insertDsl(request)
    }
    
    @Transactional
    override fun getTicketsDsl(): List<TicketResponse> {
        return ticketRepository.selectDsl()
            .map {
                TicketResponse(
                    it.id.toString(), it.user.id.toString(), it.description, it.status, it.createdAt, it.updatedAt,
                    it.attachments.map {
                        AttachmentResponse(
                            it.id.toString(), it.fileKey
                        )
                    }
                )
            }
    }
    
    @Transactional
    override fun deleteTicketDsl(ticketId: String) {
        ticketRepository.deleteDsl(ticketId)
    }
}