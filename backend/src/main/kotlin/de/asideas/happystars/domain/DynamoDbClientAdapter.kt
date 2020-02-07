package de.asideas.happystars.domain

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import javax.inject.Singleton

@Singleton
class DynamoDbClientAdapter {

    val client by lazy {
        DynamoDbAsyncClient.builder()
                .region(Region.EU_WEST_1)
                .build()
    }

}
