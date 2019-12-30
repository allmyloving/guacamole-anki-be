package com.guacamole.controller.cards.repository

import com.guacamole.controller.cards.model.CardsModel
import com.guacamole.controller.cards.model.CreateOrUpdateCardRequest

interface CardsRepository {
    fun getAll(): CardsModel
    fun create(request: CreateOrUpdateCardRequest): String
    fun update(id: String, request: CreateOrUpdateCardRequest)
    fun delete(id: String)
}
