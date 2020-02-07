package de.asideas.happystars.domain

import io.quarkus.test.Mock
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClientBuilder
import software.amazon.awssdk.services.dynamodb.model.*
import java.net.URI
import javax.annotation.PreDestroy
import javax.inject.Singleton

@Mock
@Singleton
class DynamoDbClientAdapterMock : DynamoDbClientAdapter() {

    private val dynamoDB = KGenericContainer("amazon/dynamodb-local:1.12.0")
            .withExposedPorts(8000)

    private lateinit var _client: DynamoDbAsyncClient

    override val client by lazy {
        if (!dynamoDB.isRunning) {
            dynamoDB.start()
            this._client = getAsyncClient()
            ensureTables(this._client)
        }
        this._client
    }

    private fun getAsyncClient(): DynamoDbAsyncClient {
        val endpointUri = "http://" + dynamoDB.containerIpAddress + ":" +
                dynamoDB.getMappedPort(8000)
        val builder: DynamoDbAsyncClientBuilder = DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create(endpointUri))
                .region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials
                                .create("acc", "sec")))
        return builder.build()
    }

    private fun ensureTables(client: DynamoDbAsyncClient) {
        val tables = client.listTables().join()
        if (!tables.tableNames().contains(Universe.TABLE_NAME)) {
            createUniverseTable(client)
        }
        if (!tables.tableNames().contains(Star.TABLE_NAME)) {
            createStarTable(client)
        }
    }

    fun createUniverseTable(client: DynamoDbAsyncClient) {
        client.createTable(CreateTableRequest.builder()
                .tableName(Universe.TABLE_NAME)
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.N).build(),
                        AttributeDefinition.builder().attributeName("name").attributeType(ScalarAttributeType.S).build()
                )
                .keySchema(
                        KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build(),
                        KeySchemaElement.builder().attributeName("name").keyType(KeyType.RANGE).build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput.builder().readCapacityUnits(1).writeCapacityUnits(1).build()
                )
                .build()
        ).join()
    }

    private fun createStarTable(client: DynamoDbAsyncClient) {
        // TODO("Needs implementation")
    }

    @PreDestroy
    fun shutdownDb() {
        dynamoDB.stop()
    }

}
