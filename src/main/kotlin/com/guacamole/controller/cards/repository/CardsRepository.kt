package com.guacamole.controller.cards.repository

import com.guacamole.controller.cards.model.CardsModel
import com.guacamole.controller.cards.model.CreateCardRequest

interface CardsRepository {
    fun getAll(): CardsModel
    fun create(request: CreateCardRequest): String
}
