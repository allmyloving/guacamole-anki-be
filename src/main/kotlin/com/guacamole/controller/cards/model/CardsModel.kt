package com.guacamole.controller.cards.model

data class CardsModel(val cards: List<CardModel>)

data class CardModel(
        val id: String,
        val language: String,
        val original: String,
        val translation: String,
        val example: String,
        val definition: String
)