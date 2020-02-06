package de.asideas.happystars

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception
import javax.inject.Named


@Named("stars")
class StarsHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private val log: Logger = LoggerFactory.getLogger(StarsHandler::class.java)

    private val stars = listOf(Star("Earth", 1, 1, "BLUE"), Star("Mars", 2, 1, "RED"))

    var mapper: ObjectMapper = ObjectMapper()

    init {
        mapper.registerModule(KotlinModule())
    }

    override fun handleRequest(request: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        return try {
            return when (request.httpMethod) {
                "POST" -> {
                    try {
                        createStar(mapper.readValue(request.body, CreateStarCmd::class.java))
                    } catch (jsonE: JsonProcessingException) {
                        log.error("Failed to parse request body to creation command {}.", request.body, jsonE)
                        APIGatewayProxyResponseEvent().withBody(writeException(jsonE)).withStatusCode(400)
                    }
                }
                "DELETE" -> deleteStar(request.pathParameters.get("id")!!)
                else -> getStar(request.pathParameters?.get("id"))
            }
        } catch (e: Exception) {
            log.error("Error processing request", e)
            APIGatewayProxyResponseEvent().withBody(writeException(e)).withStatusCode(500)
        }
    }


    private fun writeException(e: Exception) = e.message

    private fun deleteStar(id: String): APIGatewayProxyResponseEvent {
        val starId = id.toInt()
        return when (val star = stars.find { it.id == starId }) {
            null -> APIGatewayProxyResponseEvent().withStatusCode(404)
            else -> {
                log.info("About to delete star {}", star)
                APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(stars - star)).withStatusCode(200)
            }
        }
    }

    private fun createStar(creationCmd: CreateStarCmd): APIGatewayProxyResponseEvent {
        val newId = stars.maxBy { it.id }!!.id + 1

        val newStar = Star(creationCmd.name, newId, creationCmd.universeId, creationCmd.color)

        log.info("About to create new star {}", newStar)

        return APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(stars + newStar)).withStatusCode(201)
    }

    private fun getStar(id: String?): APIGatewayProxyResponseEvent {
        return try {
            return when (val starId = id?.toIntOrNull()) {
                is Int -> when (val star = stars.find { it.id == starId }) {
                    null -> APIGatewayProxyResponseEvent().withStatusCode(404)
                    else -> APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(star)).withStatusCode(200)
                }
                else -> APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(stars)).withStatusCode(200)
            }
        } catch (e: Exception) {
            log.error("Error processing", e)
            APIGatewayProxyResponseEvent().withBody(e.message).withStatusCode(500)
        }
    }

}
