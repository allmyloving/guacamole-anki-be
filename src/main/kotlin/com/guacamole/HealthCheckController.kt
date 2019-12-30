package com.guacamole

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @RequestMapping("/health-check")
    fun healthCheck() = HealthCheckModel("OK")
}

data class HealthCheckModel(val status: String)
