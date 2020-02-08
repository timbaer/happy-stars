package de.asideas.happystars.domain

import org.testcontainers.containers.GenericContainer

class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName) {
    companion object
}

fun KGenericContainer.Companion.createDynamoDB(version: String = "1.12.0", port: Int = 8000)
        = KGenericContainer("amazon/dynamodb-local:$version").withExposedPorts(port)
