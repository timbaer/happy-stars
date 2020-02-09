package de.asideas.happystars

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.asideas.happystars.domain.UniverseRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Named

@Named("universes")
class UniversesHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private val log: Logger = LoggerFactory.getLogger(UniversesHandler::class.java)

    @Inject
    lateinit var universeRepository: UniverseRepository

    private var mapper: ObjectMapper = ObjectMapper()

    init {
        mapper.registerModule(KotlinModule())
    }

    override fun handleRequest(request: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        return try {
            return when (request.httpMethod) {
                "POST" -> {
                    try {
                        createUniverse(mapper.readValue(request.body, CreateUniverseCmd::class.java))
                    } catch (jsonE: JsonProcessingException) {
                        log.error("Failed to parse request body to creation command {}.", request.body, jsonE)
                        APIGatewayProxyResponseEvent().withBody(writeException(jsonE)).withStatusCode(400)
                    }
                }
                "DELETE" -> deleteUniverse(request.pathParameters.get("id")!!)
                else -> getUniverse(request.pathParameters?.get("id"))
            }
        } catch (e: Exception) {
            log.error("Error processing request", e)
            APIGatewayProxyResponseEvent().withBody(writeException(e)).withStatusCode(500)
        }
    }

    private fun writeException(e: Exception) = e.message

    private fun deleteUniverse(id: String): APIGatewayProxyResponseEvent {
        val universesId = id.toInt()
        val allUni = universeRepository.getAll().get()
        return when (val universe = allUni.find { it.id == universesId}) {
            null -> APIGatewayProxyResponseEvent().withStatusCode(404)
            else -> {
                log.info("About to delete universe {}", universe)
                universeRepository.delete(id.toInt())
                APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(allUni - universe)).withStatusCode(200)
            }
        }
    }

    private fun createUniverse(creationCmd: CreateUniverseCmd): APIGatewayProxyResponseEvent {
        val allUni = universeRepository.getAll().get()
        val newId = allUni.maxBy { it.id }!!.id + 1

        val newUniverse = Universe(creationCmd.name, newId, creationCmd.maxSize)

        log.info("About to create new universe {}", newUniverse)

        return APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(allUni + newUniverse)).withStatusCode(201)
    }

    private fun getUniverse(id: String?): APIGatewayProxyResponseEvent {
        return try {
            return when (val universesId = id?.toIntOrNull()) {
                is Int -> when (val universe = universeRepository.getUniverse(universesId).get()) {
                    null -> APIGatewayProxyResponseEvent().withStatusCode(404)
                    else -> APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString(universe)).withStatusCode(200)
                }
                else -> APIGatewayProxyResponseEvent().withBody(mapper.writeValueAsString
                (universeRepository.getAll().get())).withStatusCode(200)
            }
        } catch (e: Exception) {
            log.error("Error processing", e)
            APIGatewayProxyResponseEvent().withBody(e.message).withStatusCode(500)
        }
    }
}
