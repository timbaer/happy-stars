package de.asideas.happystars.domain

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import javax.inject.Inject

@QuarkusTest
class UniverseRepositoryTest {

    @Inject
    private lateinit var universeRepository: UniverseRepository

    @Inject
    private lateinit var dynamoDbClientAdapter: DynamoDbClientAdapter

    @Test
    fun get_ShouldReturnExistingUniverse() {
        givenNoUniversesExists()
        assertNull(universeRepository.getUniverse(1).join())
        givenUniverseExists(Universe(1, "test"))
        val response = universeRepository.getUniverse(1).join()

        assertNotNull(response)
    }

    @Test
    fun get_ShouldReturnNull_ForUnknownUniverse() {
        givenNoUniversesExists()

        val response = universeRepository.getUniverse(Int.MAX_VALUE).join()

        assertNull(response)
    }

    @Test
    fun save_ShouldPersist_Universe() {
        givenNoUniversesExists()
        assertNull(universeRepository.getUniverse(1).join())

        val response = universeRepository.save(Universe(1, "test", 1000)).join()

        val expectedExisting = universeRepository.getUniverse(1).join()
        assertNotNull(expectedExisting)
        assertEquals(1, expectedExisting!!.id)
        assertEquals("test", expectedExisting.name)
        assertEquals(1000, expectedExisting.maxSize)
    }

    @Test
    fun save_ShouldPersist_Universe_And_Return_New_Values_If_Requested() {
        givenNoUniversesExists()
        assertNull(universeRepository.getUniverse(1).join())

        val response = universeRepository.save(Universe(1, "test"), true).join()
        assertNotNull(response)
        assertEquals(1, response!!.id)
        assertEquals("test", response.name)
    }

    @Test
    fun delete_ById_ShouldDeletePersistedUniverse() {
        givenUniverseExists(Universe(1, "test"))

        val response = universeRepository.delete(1).join()
        assertNotNull(response)
        assertEquals(1, response!!.id)
        assertEquals("test", response.name)

        assertNull(universeRepository.getUniverse(1).join())
    }
    @Test
    fun delete_ById_Should_Throw_ObjectNotFoundException_For_Unknown_Id() {
        givenNoUniversesExists()
        assertNull(universeRepository.delete(1)
                .exceptionally { throwable ->
                    assertThat(throwable).hasCauseExactlyInstanceOf(ObjectNotFoundException::class.java); null
                }.join())
    }

    @Test
    fun delete_ShouldDeletePersistedUniverse() {
        val universe = Universe(1, "test")
        givenUniverseExists(universe)

        val response = universeRepository.delete(universe).join()
        assertNotNull(response)
        assertEquals(1, response!!.id)
        assertEquals("test", response.name)

        assertNull(universeRepository.getUniverse(1).join())
    }

    @Test
    fun delete_Should_Return_Null_ToSignal_Nothing_Changed_For_Unknown_Universe() {
        givenNoUniversesExists()
        assertNull(universeRepository.delete(Universe(1, "totallyNotExisting")).join())
    }

    private fun givenUniverseExists(universe: Universe) {
        dynamoDbClientAdapter.client.putItem(
                PutItemRequest.builder().tableName(Universe.TABLE_NAME)
                        .item(
                                mapOf(
                                        "id" to AttributeValue.builder().n(universe.id.toString()).build(),
                                        "name" to AttributeValue.builder().s(universe.name).build(),
                                        "maxSize" to AttributeValue.builder().n(universe.maxSize.toString()).build()
                                )
                        )
                        .build()
        ).join()
    }

    private fun givenNoUniversesExists() {
        dynamoDbClientAdapter.client.deleteTable(DeleteTableRequest.builder().tableName(Universe.TABLE_NAME).build()).join()
        (dynamoDbClientAdapter as DynamoDbClientAdapterMock).createUniverseTable(dynamoDbClientAdapter.client)
    }

}
