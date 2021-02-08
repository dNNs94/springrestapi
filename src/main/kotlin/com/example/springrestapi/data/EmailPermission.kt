package com.example.springrestapi.data

import javax.persistence.*

@Entity(name = "email_permissions")
data class EmailPermission(
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
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null
)