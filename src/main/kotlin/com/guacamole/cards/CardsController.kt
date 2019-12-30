package com.guacamole.cards

import com.guacamole.cards.model.CardsModel
import com.guacamole.cards.model.ChangeRevisionPeriodRequest
import com.guacamole.cards.model.CreateCardResponse
import com.guacamole.cards.model.CreateOrUpdateCardRequest
import com.guacamole.cards.repository.CardsRepository
import org.springframework.web.bind.annotation.*

@RestController
class CardsController(
        private val cardsRepository: CardsRepository
) {
    @GetMapping("/cards")
    fun get() = CardsModel.from(cardsRepository.getAll())

    @PostMapping("/cards")
    fun create(@RequestBody request: CreateOrUpdateCardRequest) = CreateCardResponse(cardsRepository.create(request))

    @PutMapping("/cards/{id}")
    fun update(@PathVariable id: String, @RequestBody request: CreateOrUpdateCardRequest) = cardsRepository.update(id, request)

    @DeleteMapping("/cards/{id}")
    fun delete(@PathVariable id: String) = cardsRepository.delete(id)

    @PatchMapping("/cards/{id}")
    fun changeRevisionPeriod(@PathVariable id: String, @RequestBody request: ChangeRevisionPeriodRequest) = cardsRepository.changeRevisionPeriod(id, request)
}