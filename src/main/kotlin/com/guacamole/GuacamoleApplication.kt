package com.guacamole

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GuacamoleApplication

fun main(args: Array<String>) {
    runApplication<GuacamoleApplication>(*args)
}
