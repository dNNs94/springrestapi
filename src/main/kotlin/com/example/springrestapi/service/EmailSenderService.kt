package com.example.springrestapi.service

import com.example.springrestapi.util.Utils
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderService (private val emailSender: JavaMailSender, private val env: Environment) {

    fun sendEmail(subject: String, textBody: String, targetEmail: String) {

        val message = SimpleMailMessage()
        val debugMode = env.getProperty("spring.mail.properties.mail.prevent_sending").toBoolean()

        println("Debug Mode Active: " + debugMode)

        message.setSubject(subject)
        message.setText(textBody)
        message.setTo(targetEmail)

        if(debugMode) {
            val stringBuilder = StringBuilder()

            stringBuilder.append("Recipent: " + message.to.contentToString())
            stringBuilder.append(Utils.newLine())
            stringBuilder.append("Subject: " + message.subject)
            stringBuilder.append(Utils.newLine())
            stringBuilder.append("Text: " + message.text)

            println(stringBuilder.toString())
        }
        else {
            emailSender.send(message)
            println("Mail sent to: " + message.to.contentToString())
        }
    }
}