package com.example.springrestapi.interfaces

import com.example.springrestapi.data.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByAccountId(accountId: Long): User
}