package de.asideas

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

import org.apache.logging.log4j.LogManager
import java.util.*

class UniversesHandler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        LOG.info("received: " + input.keys.toString())

        val responseBody = UniversesResponse(listOf(Universe("Sebastian")))

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = responseBody
            headers = Collections.singletonMap<String, String>("X-Powered-By", "AWS Lambda & serverless")
        }
    }

    companion object {
        private val LOG = LogManager.getLogger(UniversesHandler::class.java)
    }
}
