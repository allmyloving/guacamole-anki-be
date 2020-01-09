package com.guacamole.cards.repository

import com.google.cloud.Timestamp
import com.google.cloud.datastore.*
import com.google.cloud.datastore.StructuredQuery.OrderBy.*
import com.guacamole.cards.domain.Card
import com.guacamole.cards.domain.RevisionPeriod
import com.guacamole.cards.model.ChangeRevisionPeriodRequest
import com.guacamole.cards.model.CreateOrUpdateCardRequest
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.temporal.ChronoUnit

@Repository
class DatastoreCardsRepository(private val datastore: Datastore) : CardsRepository {
    override fun getAll(): List<Card> {
        val query = Query.newEntityQueryBuilder()
                .setKind("Card")
                .addOrderBy(asc("nextRevision"))
                .build();
        val cards = mutableListOf<Card>()
        val queryResults = datastore.run(query)
        queryResults.forEach {
            cards.add(it.toCard())
        }
        return cards
    }

    private fun Entity.toCard(): Card {
        return Card(
                id = key.id.toString(),
                language = getString("language"),
                original = getString("word"),
                example = getString("example"),
                definition = getString("definition"),
                translation = getString("translation"),
                nextRevision = Instant.ofEpochSecond(getTimestamp("nextRevision").seconds),
                revisionPeriod = RevisionPeriod.valueOf(getString("revisionPeriod"))
        )
    }

    override fun create(request: CreateOrUpdateCardRequest): String {
        // TODO Make KeyFactory a bean
        val keyFactory = datastore.newKeyFactory()
                .setKind("Card")

        val key = datastore.allocateId(keyFactory.newKey())
        val entity = request.toEntity(key)
                .set("nextRevision", Instant.now().toTimestamp())
                .set("revisionPeriod", RevisionPeriod.ONE_DAY.name)
                .build()
        return datastore.add(entity).key.id.toString()
    }

    override fun update(id: String, request: CreateOrUpdateCardRequest) {
        val key = datastore.newKeyFactory()
                .setKind("Card")
                .newKey(id.toLong())

        val entity = datastore.get(key).toCard()
        datastore.update(request.toEntity(key)
                .set("nextRevision", entity.nextRevision.toTimestamp())
                .set("revisionPeriod", entity.revisionPeriod.toString())
                .build())
    }

    override fun delete(id: String) {
        val key = datastore.newKeyFactory()
                .setKind("Card")
                .newKey(id.toLong())
        datastore.delete(key)
    }

    private fun daysFromToday(days: Int) = Instant.now().plus(days.toLong(), ChronoUnit.DAYS).toTimestamp()

    private fun Instant.toTimestamp() = Timestamp.ofTimeSecondsAndNanos(epochSecond, nano)

    override fun changeRevisionPeriod(id: String, request: ChangeRevisionPeriodRequest) {
        val key = datastore.newKeyFactory()
                .setKind("Card")
                .newKey(id.toLong())
        val entity = datastore.get(key)
        val card = entity.toCard()
        val newRevisionPeriod = card.revisionPeriod.let { if (request.success) it.next() else it.previous() }
        datastore.update(Entity.newBuilder(entity)
                .set("nextRevision", daysFromToday(newRevisionPeriod.days))
                .set("revisionPeriod", newRevisionPeriod.name)
                .build())
    }


    private fun CreateOrUpdateCardRequest.toEntity(key: Key) = Entity.newBuilder(key)
            .set("word", original)
            .set("translation", translation)
            .set("language", language)
            .set("definition", definition)
            .set("example", example)
            .set("lastModified", Timestamp.now())
}
