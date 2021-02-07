package com.example.springrestapi.data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "users")
data class User(
    val accountId: Long,
    val email: String,
    val name: String,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null
)
