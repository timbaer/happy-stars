package de.asideas.happystars.domain

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

interface DynamoDbClientAdapter {
    fun client() : DynamoDbAsyncClient
}