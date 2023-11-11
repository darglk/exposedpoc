package com.darglk.exposedpoc.repository

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant
import java.util.*

enum class UserStatus {
    ENABLED, BLOCKED
}

enum class TicketStatus {
    CREATED, IN_PROGRESS, COMPLETED
}

object Users : UUIDTable("users") {
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val password: Column<String> = varchar("password", 100)
    val status: Column<String> = varchar("status", 20)
}

object Authorities : UUIDTable("authorities") {
    val name: Column<String> = varchar("name", 36).uniqueIndex()
}

class User(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)
    var email by Users.email
    var password by Users.password
    var status by Users.status
    var authorities by Authority via UsersAuthorities
}

class Authority(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Authority>(Authorities)
    var name by Authorities.name
}

object UsersAuthorities : Table("users_authorities") {
    val user = reference("user_id", Users)
    val authority = reference("authority_id", Authorities)
    override val primaryKey = PrimaryKey(user, authority)
}

object Tickets : UUIDTable("tickets") {
    val title: Column<String> = varchar("title", 255).default("")
    val description: Column<String> = text("description").default("")
    val status: Column<String> = varchar("status", 20).default("")
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant> = timestamp("updated_at")
    val user = reference("user_id", Users)
}

class Ticket(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Ticket>(Tickets)
    var title by Tickets.title
    var description by Tickets.description
    var status by Tickets.status
    var createdAt by Tickets.createdAt
    var updatedAt by Tickets.updatedAt
    var user by User referencedOn Tickets.user
    val attachments by Attachment referrersOn Attachments.ticket
}

object Attachments : UUIDTable("attachments") {
    val fileKey: Column<String> = text("file_key")
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant> = timestamp("updated_at")
    var ticket = reference("ticket_id", Tickets)
}

class Attachment(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Attachment>(Attachments)
    var fileKey by Attachments.fileKey
    var createdAt by Attachments.createdAt
    var updatedAt by Attachments.updatedAt
    var ticket by Ticket referencedOn Attachments.ticket
}