package de.asideas.happystars.domain

import io.quarkus.test.Mock
import org.eclipse.microprofile.config.ConfigProvider
import software.amazon.awssdk.arns.Arn
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
class DynamoDbClientAdapterMock: DynamoDbClientAdapter {

    private val dynamoDB = KGenericContainer.createDynamoDB()

    lateinit var _client : DynamoDbAsyncClient

    val universesDynamoDbTableName by lazy {
        Arn.fromString(ConfigProvider.getConfig()
                .getValue("universes.dynamodb.arn", String::class.java)).resource().resource()
    }

    init {
        if (!dynamoDB.isRunning) {
            dynamoDB.start()
            this._client = getAsyncClient()
            ensureTables()
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

    private fun ensureTables() {
        val tables = _client.listTables().join()
        if (!tables.tableNames().contains(universesDynamoDbTableName)) {
            createUniverseTable()
        }
        if (!tables.tableNames().contains(Star.TABLE_NAME)) {
            createStarTable()
        }
    }

    fun createUniverseTable() {
        _client.createTable(CreateTableRequest.builder()
                .tableName(universesDynamoDbTableName)
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

    private fun createStarTable() {
        // TODO("Needs implementation")
    }

    @PreDestroy
    fun shutdownDb() {
        dynamoDB.stop()
    }

    override fun client(): DynamoDbAsyncClient {
        return _client
    }

}
