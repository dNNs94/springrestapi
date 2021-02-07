package com.example.springrestapi.data

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name="emails")
data class Email(
    val senderId: Long,
    val receiverId: Long,
    val timestamp: Timestamp,
    @Id @GeneratedValue val id: Long? = null
)
