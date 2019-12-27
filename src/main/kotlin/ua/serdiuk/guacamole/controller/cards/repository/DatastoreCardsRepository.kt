package ua.serdiuk.guacamole.controller.cards.repository

import com.google.cloud.Timestamp
import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.Query
import org.springframework.stereotype.Repository
import ua.serdiuk.guacamole.controller.cards.model.CardModel
import ua.serdiuk.guacamole.controller.cards.model.CardsModel
import ua.serdiuk.guacamole.controller.cards.model.CreateCardRequest


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

    override fun create(request: CreateCardRequest): String {
        val keyFactory = datastore.newKeyFactory()
                .setKind("Card")
        val key = datastore.allocateId(keyFactory.newKey())
        val newCard = Entity.newBuilder(key)
                .set("word", request.original)
                .set("translation", request.translation)
                .set("language", request.language)
                .set("definition", request.definition)
                .set("example", request.example)
                .set("dateAdded", Timestamp.now())
                .build()
        return datastore.add(newCard).key.id.toString()
    }
}
