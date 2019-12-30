package com.guacamole.controller.cards.repository

import com.google.cloud.Timestamp
import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.Key
import com.google.cloud.datastore.Query
import com.guacamole.controller.cards.model.CardModel
import com.guacamole.controller.cards.model.CardsModel
import com.guacamole.controller.cards.model.CreateOrUpdateCardRequest
import org.springframework.stereotype.Repository


@Repository
class DatastoreCardsRepository(private val datastore: Datastore) : CardsRepository {
    override fun getAll() = CardsModel(
            cards = getFromDataStore()
    )

    private fun getFromDataStore(): MutableList<CardModel> {
        val query = Query.newEntityQueryBuilder()
                .setKind("Card")
                .build();
        val cards = mutableListOf<CardModel>()
        val queryResults = datastore.run(query)
        queryResults.forEach {
            cards.add(CardModel(
                    id = it.key.id.toString(),
                    language = it.getString("language"),
                    original = it.getString("word"),
                    example = it.getString("example"),
                    definition = it.getString("definition"),
                    translation = it.getString("translation")
            ))
        }
        return cards

    }

    override fun create(request: CreateOrUpdateCardRequest): String {
        // TODO Make KeyFactory a bean
        val keyFactory = datastore.newKeyFactory()
                .setKind("Card")
        val key = datastore.allocateId(keyFactory.newKey())
        return datastore.add(request.toEntity(key))
                .key.id.toString()
    }

    override fun update(id: String, request: CreateOrUpdateCardRequest) {
        val key = datastore.newKeyFactory()
                .setKind("Card")
                .newKey(id.toLong())
        datastore.update(request.toEntity(key))
    }

    override fun delete(id: String) {
        val key = datastore.newKeyFactory()
                .setKind("Card")
                .newKey(id.toLong())
        datastore.delete(key)
    }

    private fun CreateOrUpdateCardRequest.toEntity(key: Key): Entity? {
        return Entity.newBuilder(key)
                .set("word", original)
                .set("translation", translation)
                .set("language", language)
                .set("definition", definition)
                .set("example", example)
                .set("lastModified", Timestamp.now())
                .build()
    }
}
