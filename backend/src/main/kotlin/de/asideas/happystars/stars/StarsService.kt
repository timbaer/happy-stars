package de.asideas.happystars.stars

import de.asideas.happystars.CreateStarCmd
import de.asideas.happystars.Star
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

class StarsService {

    var ddb : DynamoDbClient? = null

    init {
        ddb  = DynamoDbClient.builder().region(Region.EU_WEST_1)
                .credentialsProvider(ProfileCredentialsProvider.builder().profileName
                ("id-playground").build())
                .build()
    }

    fun create(creationCmd: CreateStarCmd) : Star{

        val newStar = Star(creationCmd.name, 778, creationCmd.universeId, creationCmd.color)

        val starItem = mapOf(
                "name" to AttributeValue.builder()
                        .s(newStar.name)
                        .build(),
                "color" to AttributeValue.builder()
                        .s(newStar.name)
                        .build(),
                "id" to AttributeValue.builder()
                        .s(newStar.id.toString())
                        .build()
        )

        val tableName = "happStarsUniverses-dev"
        val request = PutItemRequest.builder()
                .tableName(tableName)
                .item(starItem)
                .build()

        ddb?.putItem(request)

        return newStar

    }

}