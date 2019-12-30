package com.guacamole.cards.model

import com.guacamole.cards.domain.Card
import java.time.Instant

data class CardsModel(val cards: List<CardModel>) {
    companion object {
        fun from(cards: List<Card>) = CardsModel(
                cards = cards.map {
                    CardModel(
                            id = it.id,
                            language = it.language,
                            original = it.original,
                            translation = it.translation,
                            example = it.example,
                            definition = it.definition,
                            readyForRevision = it.nextRevision.isBefore(Instant.now())
                    )
                }
        )
    }
}

data class CardModel(
        val id: String,
        val language: String,
        val original: String,
        val translation: String,
        val example: String,
        val definition: String,
        val readyForRevision: Boolean
)