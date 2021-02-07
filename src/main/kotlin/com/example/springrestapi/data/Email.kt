package com.example.springrestapi.data

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name="emails")
data class Email (
    val senderId: Long,
    val receiverId: Long,
    val timestamp: Timestamp,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null
)