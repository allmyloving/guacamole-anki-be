package com.guacamole.controller.cards

import com.guacamole.controller.cards.model.CreateCardRequest
import com.guacamole.controller.cards.model.CreateCardResponse
import com.guacamole.controller.cards.repository.CardsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsController(
        private val cardsRepository: CardsRepository
) {
    @GetMapping("/cards")
    fun get() = cardsRepository.getAll()

    @PostMapping("/cards")
    fun create(@RequestBody request: CreateCardRequest) = CreateCardResponse(cardsRepository.create(request))
}