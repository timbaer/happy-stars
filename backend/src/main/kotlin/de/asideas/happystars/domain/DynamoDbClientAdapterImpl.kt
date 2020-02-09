package de.asideas.happystars.domain

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamoDbClientAdapterImpl : DynamoDbClientAdapter{

    @Inject
    lateinit var _client: DynamoDbAsyncClient

    override fun client(): DynamoDbAsyncClient {
        return _client
    }
}
