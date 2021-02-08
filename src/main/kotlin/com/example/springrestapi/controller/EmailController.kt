package com.example.springrestapi.controller

import com.example.springrestapi.data.Email
import com.example.springrestapi.data.User
import com.example.springrestapi.interfaces.EmailPermissionRepository
import com.example.springrestapi.interfaces.EmailRepository
import com.example.springrestapi.interfaces.UserRepository
import com.example.springrestapi.service.EmailSenderService
import com.example.springrestapi.util.Utils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.StringBuilder
import java.sql.Timestamp

@RestController
class EmailController (
    private val permissionRepository: EmailPermissionRepository,
    private val userRepository: UserRepository,
    private val emailRepository: EmailRepository,
    private val emailSenderService: EmailSenderService) {

    //region GET-Methods
    @GetMapping("/{senderId}/receivers")
    fun getPermittedAccounts(@PathVariable senderId: Long): String {
        val permittedAccounts = permissionRepository.findAllBySenderId(senderId)
        val stringBuilder = StringBuilder()

        stringBuilder.append("Allowed to send emails to Accounts owned by: ")

        for(i in permittedAccounts.indices) {
            stringBuilder.append(permittedAccounts[i].receiver.id)
            stringBuilder.append(" - " + permittedAccounts[i].receiver.name)
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
            val user = userRepository.findById(sentMails[i].receiver.id!!).get()

            stringBuilder.append("Receiver: " + getUserString(user))
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
            val user = userRepository.findById(receivedMails[i].sender.id!!).get()

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
            if(permittedAccounts[i].receiver.id == receiverId) {
                permitted = true

                val sender = userRepository.findById(senderId).get()
                val receiver = userRepository.findById(receiverId).get()
                val email = Email(sender, receiver, Timestamp(System.currentTimeMillis()))

                stringBuilder.append("Hallo " + receiver.name + ", ich bin's " + sender.name)
                stringBuilder.append(Utils.newLine())
                stringBuilder.append("Diese Mail wurde durch einen Spring REST Service versendet.")
                stringBuilder.append(Utils.newLine())
                stringBuilder.append("Für Eindrücke zum aktuellen Stand schau gern hier vorbei: ")
                stringBuilder.append("https://github.com/dNNs94/springrestapi")
                stringBuilder.append(Utils.newLine())
                stringBuilder.append("Falls du mir Feedback da lassen möchtest, hast du meine richtige Mail-Adresse ja bereits.")
                stringBuilder.append(Utils.newLine())
                stringBuilder.append(Utils.newLine())
                stringBuilder.append("Mit freundlichen Grüßen ")
                stringBuilder.append(Utils.newLine())
                stringBuilder.append(sender.name + " <" + sender.email + ">")
                stringBuilder.append(Utils.newLine())
                stringBuilder.append(Utils.newLine())
                stringBuilder.append("PS: Sorry, mir ist kein besserer Name für die Wegwerf-Adresse eingefallen.")

                emailRepository.save(email)
                emailSenderService.sendEmail("Grüße von Dennis - Kotlin Spring Mail Automatisierung", stringBuilder.toString(), receiver.email)
                break
            }

            permitted = false
        }

        return if(permitted) {
            //println(stringBuilder.toString())
            stringBuilder.toString()
        } else {
            stringBuilder.append("Permission denied")
            //println(stringBuilder.toString())
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