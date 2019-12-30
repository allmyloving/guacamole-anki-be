package com.guacamole.controller.cards.model

data class CreateCardRequest(
        val language: String,
        val original: String,
        val translation: String,
        val example: String?,
        val definition: String?
)