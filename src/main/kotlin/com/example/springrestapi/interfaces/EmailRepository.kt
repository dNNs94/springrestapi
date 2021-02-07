package com.example.springrestapi.interfaces

import com.example.springrestapi.data.Email
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailRepository: CrudRepository<Email, Long> {
    fun findAllByReceiverId(receiverId: Long): MutableList<Email>
    fun findAllBySenderId(senderId: Long): MutableList<Email>
}