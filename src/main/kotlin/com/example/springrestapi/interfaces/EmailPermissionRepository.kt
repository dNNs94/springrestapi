package com.example.springrestapi.interfaces

import com.example.springrestapi.data.EmailPermission
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailPermissionRepository: CrudRepository<EmailPermission, Long> {
    fun findAllBySenderId(senderId: Long): MutableList<EmailPermission>
    fun findAllByReceiverId(receiverId: Long): MutableList<EmailPermission>
}