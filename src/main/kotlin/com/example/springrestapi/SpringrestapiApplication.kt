package com.example.springrestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringrestapiApplication

/**
 * ToDo: REFACTOR accountID TO BE users.id!!!!
 *
 * ToDo: Do REST-Controller to define routes - CHECK
 *  -> Get User by Sending User by Account ID -> Get Receiver by account ID -> Send Mail
 *
 * ToDo: Logics to send mails
 *  -> Create Dumping Email-Adress
 *  -> Figure out how to send mails to yourself
 *  -> Add property to application.properties to define whether to send real mails or just log mail output to console
 *
 * ToDo: Save Emails to Database (senderId, receiverId, timestamp) - CHECK
 *
 * ToDo: REST Endpoints to get sent/received Emails - CHECK
 *
 * ToDo: Spring Security -> Password Protection on REST Endpoints
 *  -> Password column in Users Table -> Use for Password Request
 *  -> Restrict Access to Endpoints via User-Login (Only account with accountId == senderId can view e.g /{senderID}/sentMails
 *
 * ToDo: Thymeleaf to define HTML Templates for Emails
 *  -> Internationalize messages (ger/eng) using .properties files
 */

fun main(args: Array<String>) {
    runApplication<SpringrestapiApplication>(*args)
}
