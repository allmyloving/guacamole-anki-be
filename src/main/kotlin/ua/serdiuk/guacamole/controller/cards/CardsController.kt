package ua.serdiuk.guacamole.controller.cards

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ua.serdiuk.guacamole.controller.cards.model.CreateCardRequest
import ua.serdiuk.guacamole.controller.cards.model.CreateCardResponse
import ua.serdiuk.guacamole.controller.cards.repository.CardsRepository

@RestController
class CardsController(
        private val cardsRepository: CardsRepository
) {
    @GetMapping("/cards")
    fun get() = cardsRepository.getAll()

    @PostMapping("/cards")
    fun create(@RequestBody request: CreateCardRequest) = CreateCardResponse(cardsRepository.create(request))
}