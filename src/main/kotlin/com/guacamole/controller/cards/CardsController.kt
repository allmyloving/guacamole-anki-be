package com.guacamole.controller.cards

import com.guacamole.controller.cards.model.CreateOrUpdateCardRequest
import com.guacamole.controller.cards.model.CreateCardResponse
import com.guacamole.controller.cards.repository.CardsRepository
import org.springframework.web.bind.annotation.*

@RestController
class CardsController(
        private val cardsRepository: CardsRepository
) {
    @GetMapping("/cards")
    fun get() = cardsRepository.getAll()

    @PostMapping("/cards")
    fun create(@RequestBody request: CreateOrUpdateCardRequest) = CreateCardResponse(cardsRepository.create(request))

    @PutMapping("/cards/{id}")
    fun update(@PathVariable id: String, @RequestBody request: CreateOrUpdateCardRequest) = cardsRepository.update(id, request)

    @DeleteMapping("/cards/{id}")
    fun delete(@PathVariable id: String) = cardsRepository.delete(id)
}