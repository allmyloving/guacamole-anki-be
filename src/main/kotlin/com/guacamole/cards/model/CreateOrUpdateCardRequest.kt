package com.guacamole.cards.model

data class CreateOrUpdateCardRequest(
        val language: String,
        val original: String,
        val translation: String,
        val example: String?,
        val definition: String?
)