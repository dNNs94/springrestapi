package com.example.springrestapi.data

import java.sql.Timestamp
import javax.persistence.*

@Entity(name="emails")
data class Email (
    @ManyToOne
    @JoinColumn(
        name="sender_user_id",
        referencedColumnName = "id"
    )
    val sender: User,

    @ManyToOne
    @JoinColumn(
        name="receiver_user_id",
        referencedColumnName = "id"
    )
    val receiver: User,
    val timestamp: Timestamp,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null
)