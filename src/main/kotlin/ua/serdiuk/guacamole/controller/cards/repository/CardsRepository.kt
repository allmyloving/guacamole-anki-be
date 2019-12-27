package ua.serdiuk.guacamole.controller.cards.repository

import ua.serdiuk.guacamole.controller.cards.model.CardsModel
import ua.serdiuk.guacamole.controller.cards.model.CreateCardRequest

interface CardsRepository {
    fun getAll(): CardsModel
    fun create(request: CreateCardRequest): String
}
