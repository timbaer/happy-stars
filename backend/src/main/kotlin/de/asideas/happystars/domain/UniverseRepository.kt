package de.asideas.happystars.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UniverseRepository @Inject constructor(val dynamoDbClientAdapter: DynamoDbClientAdapter) {

    private var log: Logger = LoggerFactory.getLogger(UniverseRepository::class.java)

    fun getUniverse(id: Int): CompletableFuture<Universe?> {
        val query = dynamoDbClientAdapter.client.query(QueryRequest.builder()
                .tableName(Universe.TABLE_NAME)
                .keyConditionExpression("id = :universeId")
                .expressionAttributeValues(mapOf(
                        ":universeId" to AttributeValue.builder().n(id.toString()).build()
                )).build()
        )

        return query.thenApply {
            when (it.hasItems() && it.items().size == 1) {
                true -> mapToUniverse(it.items()[0])
                else -> null
            }
        }
    }

    fun save(universe: Universe, returnSaved: Boolean = false): CompletableFuture<Universe?> {
        val attributes = mapOf(
                "id" to AttributeValue.builder().n(universe.id.toString()).build(),
                "name" to AttributeValue.builder().s(universe.name).build(),
                "maxSize" to AttributeValue.builder().n(universe.maxSize.toString()).build()
        )

        val putItem = dynamoDbClientAdapter.client.putItem(PutItemRequest.builder()
                .tableName(Universe.TABLE_NAME)
                .item(attributes)
                .build())

        return when (returnSaved) {
            true -> putItem.thenCompose {
                when (it.hasAttributes() && it.attributes().isNotEmpty()) {
                    true -> CompletableFuture.completedFuture(mapToUniverse(it.attributes()))
                    else -> getUniverse(universe.id)
                }
            }
            else -> putItem.thenApply { null }
        }
    }

    fun delete(id: Int): CompletableFuture<Universe?> = getUniverse(id).thenCompose {
        when (it) {
            null -> CompletableFuture.failedFuture(ObjectNotFoundException(id.toString(), Universe::class))
            else -> delete(it)
        }
    }

    fun delete(universe: Universe): CompletableFuture<Universe?> {
        val deleteItem = dynamoDbClientAdapter.client.deleteItem(DeleteItemRequest.builder()
                .tableName(Universe.TABLE_NAME)
                .key(mapOf(
                        "id" to AttributeValue.builder().n(universe.id.toString()).build(),
                        "name" to AttributeValue.builder().s(universe.name).build()
                ))
                .returnValues(ReturnValue.ALL_OLD)
                .build())

        return deleteItem.thenApply {
            when (it.hasAttributes() && it.attributes().isNotEmpty()) {
                true -> mapToUniverse(it.attributes())
                else -> null
            }
        }
    }

    private fun mapToUniverse(item: MutableMap<String, AttributeValue>): Universe {
        return Universe(
                item.get("id")!!.n().toInt(),
                item.get("name")!!.s(),
                item.get("maxSize")?.n()?.toInt()
        )
    }
}
