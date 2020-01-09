package com.guacamole.tags.repository

import com.guacamole.tags.domain.Tag
import com.guacamole.tags.request.CreateTagRequest

interface TagsRepository {
    fun getAll(): List<Tag>
    fun create(request: CreateTagRequest): String
    fun delete(id: String)
}