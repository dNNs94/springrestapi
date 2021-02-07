package com.example.springrestapi.data

import javax.persistence.*

@Entity(name = "email_permissions")
data class EmailPermission(
    val senderId: Long,
    val receiverId: Long,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null
)