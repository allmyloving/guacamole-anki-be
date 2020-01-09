package com.guacamole.tags.repository

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.Query
import com.guacamole.cards.domain.RevisionPeriod
import com.guacamole.tags.domain.Tag
import com.guacamole.tags.request.CreateTagRequest
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class DatastoreTagsRepository(private val datastore: Datastore) : TagsRepository {

    override fun create(request: CreateTagRequest): String {
        val keyFactory = datastore.newKeyFactory()
                .setKind("Tag")
        val key = datastore.allocateId(keyFactory.newKey())
        val entity = Entity.newBuilder(key)
                .set("title", request.title)
                .set("color", request.color)
                .build()
        return datastore.add(entity).key.id.toString()
    }

    override fun getAll(): List<Tag> {
        val query = Query.newEntityQueryBuilder()
                .setKind("Tag")
                .build();
        val tags = mutableListOf<Tag>()
        val queryResults = datastore.run(query)
        queryResults.forEach {
            tags.add(Tag(
                    id = it.key.id.toString(),
                    title = it.getString("title"),
                    color = it.getString("color")
            ))
        }
        return tags
    }

    override fun delete(id: String) {
        val key = datastore.newKeyFactory()
                .setKind("Tag")
                .newKey(id.toLong())
        datastore.delete(key)
    }
}