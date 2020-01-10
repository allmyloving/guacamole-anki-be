package com.guacamole.cards.domain

import java.time.Instant

data class Card(
        val id: String,
        val language: String,
        val original: String,
        val translation: String,
        val example: String,
        val definition: String,
        val nextRevision: Instant,
        val revisionPeriod: RevisionPeriod,
        val tags: List<String>
)