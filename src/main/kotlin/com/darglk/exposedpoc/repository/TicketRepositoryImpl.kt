package com.darglk.exposedpoc.repository

import com.darglk.exposedpoc.controller.CreateTicketRequest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
class TicketRepositoryImpl(
    private val db: Database,
    private val userRepository: UserRepository
) : TicketRepository {
    override fun insert(request: CreateTicketRequest) {
        val u = userRepository.findUser(request.userId)!!
        val t = Ticket.new {
            title = request.title
            description = request.description
            status = TicketStatus.CREATED.name
            user = u
            createdAt = Instant.now()
            updatedAt = Instant.now()
        }
        val created = Ticket.findById(t.id)
        request.attachments.forEach { kurwa ->
            Attachment.new {
                fileKey = kurwa.fileKey
                createdAt = Instant.now()
                updatedAt = Instant.now()
                ticket = created!!
            }
        }
    }
    
    override fun insertDsl(request: CreateTicketRequest) {
        val u = userRepository.findUser(request.userId)!!
        val ticketId = Tickets.insert {
            it[title] = request.title
            it[description] = request.description
            it[status] = "CREATED"
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
            it[user] = u.id
        } get Tickets.id
        request.attachments.forEach {attachment ->
            Attachments.insert {
                it[fileKey] = attachment.fileKey
                it[ticket] = ticketId
                it[createdAt] = Instant.now()
                it[updatedAt] = Instant.now()
            }
        }
    }
    
    override fun select(): List<Ticket> {
        return Ticket.all().toList()
    }
    
    override fun selectDsl(): List<Ticket> {
         val result = (Tickets innerJoin Attachments)
             .selectAll()
             .withDistinct()
        val tickets: List<Ticket> = result.map {
            Ticket.wrapRow(it)
        }.distinctBy { it.id }
        return tickets
    }
    
    override fun delete(ticketId: String) {
        Ticket.findById(UUID.fromString(ticketId))?.delete()
    }
    
    override fun deleteDsl(ticketId: String) {
        Tickets.deleteWhere { Tickets.id eq UUID.fromString(ticketId) }
    }
}