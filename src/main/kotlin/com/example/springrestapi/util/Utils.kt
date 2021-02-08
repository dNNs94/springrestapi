package com.example.springrestapi.util

import org.springframework.context.annotation.Bean

class Utils {
    companion object {
        @JvmStatic
        fun newLine(): String {
            return System.getProperty("line.separator")
        }
    }
}