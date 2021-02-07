package com.example.springrestapi.controller

import com.example.springrestapi.data.Email
import com.example.springrestapi.data.User
import com.example.springrestapi.interfaces.EmailPermissionRepository
import com.example.springrestapi.interfaces.EmailRepository
import com.example.springrestapi.interfaces.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.StringBuilder
import java.sql.Timestamp
import java.util.concurrent.atomic.AtomicLong

@RestController
class EmailPermissionController (
    private val permissionRepository: EmailPermissionRepository,
    private val userRepository: UserRepository,
    private val emailRepository: EmailRepository) {

    //region GET-Methods
    @GetMapping("/{senderId}/receivers")
    fun getPermittedAccounts(@PathVariable senderId: Long): String {
        val permittedAccounts = permissionRepository.findAllBySenderId(senderId)
        val stringBuilder = StringBuilder()

        stringBuilder.append("Allowed to send emails to Account with ID: ")

        for(i in permittedAccounts.indices) {
            stringBuilder.append(permittedAccounts[i].receiverId)

            if(i < permittedAccounts.count() - 1)
                stringBuilder.append(", ")
        }
        print(stringBuilder.toString())
        return stringBuilder.toString()
    }

    @GetMapping("/{senderId}/sentMails")
    fun getSentMails(@PathVariable senderId: Long): String {
        val sentMails = emailRepository.findAllBySenderId(senderId)
        val stringBuilder = StringBuilder()

        if(sentMails.count() == 0)
            return "Empty"

        for(i in sentMails.indices) {
            val user = userRepository.findByAccountId(sentMails[i].receiverId)

            stringBuilder.append("Recipent: " + getUserString(user))
            stringBuilder.append(", Timestamp: " + sentMails[i].timestamp)

            if (i < sentMails.count() - 1)
                stringBuilder.append(" | ")
        }

        return stringBuilder.toString()
    }

    @GetMapping("/{receiverId}/receivedMails")
    fun getReceivedMails(@PathVariable receiverId: Long): String {
        val receivedMails = emailRepository.findAllByReceiverId(receiverId)
        val stringBuilder = StringBuilder()

        if(receivedMails.count() == 0)
            return "Empty"

        for(i in receivedMails.indices) {
            val user = userRepository.findByAccountId(receivedMails[i].senderId)

            stringBuilder.append("Sender: " + getUserString(user))
            stringBuilder.append(", Timestamp: " + receivedMails[i].timestamp)

            if (i < receivedMails.count() - 1)
                stringBuilder.append(" | ")
        }

        return stringBuilder.toString()
    }
    //endregion

    //region POST-Methods
    @PostMapping("/{senderId}/send/{receiverId}")
    fun sendEmailIfPermitted(@PathVariable senderId: Long, @PathVariable receiverId: Long): String {
        val stringBuilder = StringBuilder()
        val permittedAccounts = permissionRepository.findAllBySenderId(senderId)
        var permitted = false

        for(i in permittedAccounts.indices) {
            if(permittedAccounts[i].receiverId == receiverId) {
                permitted = true

                val sender = userRepository.findByAccountId(senderId)
                val receiver = userRepository.findByAccountId(receiverId)
                val email = Email(sender.accountId, receiver.accountId, Timestamp(System.currentTimeMillis()))

                stringBuilder.append("Hallo " + receiver.name + ", ich bin " + sender.name)
                emailRepository.save(email)
                break
            }

            permitted = false
            stringBuilder.append("Permission denied")
        }

        return if(permitted) {
            println(stringBuilder.toString())
            stringBuilder.toString()
        } else {
            println(stringBuilder.toString())
            stringBuilder.toString()
        }
    }
    //endregion

    //region Helper Methods
    fun getUserString(user: User): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(user.name)
        stringBuilder.append(", E-Mail: " + user.email)

        return stringBuilder.toString()
    }
    //endregion
}