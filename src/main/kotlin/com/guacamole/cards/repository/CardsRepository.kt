package com.guacamole.cards.repository

import com.guacamole.cards.domain.Card
import com.guacamole.cards.model.ChangeRevisionPeriodRequest
import com.guacamole.cards.model.CreateOrUpdateCardRequest

interface CardsRepository {
    fun getAll(): List<Card>
    fun create(request: CreateOrUpdateCardRequest): String
    fun update(id: String, request: CreateOrUpdateCardRequest)
    fun delete(id: String)
    fun changeRevisionPeriod(id: String, request: ChangeRevisionPeriodRequest)
}
