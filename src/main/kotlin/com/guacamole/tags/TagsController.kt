package com.guacamole.tags

import com.guacamole.tags.model.TagsModel
import com.guacamole.tags.repository.TagsRepository
import com.guacamole.tags.request.CreateTagRequest
import org.springframework.web.bind.annotation.*

@RestController
class TagsController(private val tagsRepository: TagsRepository) {

    @GetMapping("/tags")
    fun getAll() = TagsModel(tagsRepository.getAll())

    @PostMapping("/tags")
    fun create(@RequestBody request: CreateTagRequest) = tagsRepository.create(request)

    @DeleteMapping("/tags/{id}")
    fun delete(@PathVariable("id") id: String) = tagsRepository.delete(id)
}