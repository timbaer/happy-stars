package de.asideas.happystars.end2end

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import de.asideas.happystars.CreateUniverseCmd
import de.asideas.happystars.domain.Universe
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URL

@QuarkusTest
class UniverseHandlerE2ETest {

    /*
     * TODO: Quarkus test doesn't support config property? in kotlin?
    @ConfigProperty(name="e2e.apigateway.url")
    @Inject
    */
    val baseEndpoint: String by lazy {
        System.getenv("E2E_APIGATEWAY_URL")!!
    }

    val mapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Test
    fun `ensure Flow- creation(POST) AND list(GET) AND find(GET) AND remove(DELETE) works as expected`() {

        val createUniverseCmd = CreateUniverseCmd("test" + (Math.random() * 10000).toInt(), (Math.random() * 100).toInt())

        // tests POST
        val createdUniverse = createUniverse(createUniverseCmd)
        assertThat(createdUniverse).isNotNull

        // tests GET w/o a specific universe ID
        assertThat(getAllUniversesAndReturnTheNewOne(createUniverseCmd)).isNotNull

        // tests GET for the specific newly create universe
        assertThat(getUniverseById(createdUniverse!!.id))
                .isEqualTo(Universe(createdUniverse.id, createUniverseCmd.name, createUniverseCmd.maxSize))

        // tests DELETE the new universe
        val allUniversesAfterDeletion = deleteUniverse(createdUniverse.id)
        assertThat(allUniversesAfterDeletion.find { it.id == createdUniverse.id }).isNull()
    }

    private fun getUniverseById(id: Int): Universe = runBlocking {
        val response = HttpClient().get<HttpResponse>("$baseEndpoint/universes/$id")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)

        val readText = response.readText()
        println("response for universe ID $id is: $readText")
        mapper.readValue(readText, Universe::class.java)
    }

    private fun deleteUniverse(id: Int): List<Universe> = runBlocking {

        val response = HttpClient().delete<HttpResponse>("$baseEndpoint/universes/$id")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)

        mapper.readValue(response.readText(), object : TypeReference<List<Universe>>() {})
    }

    private fun getAllUniversesAndReturnTheNewOne(createUniverseCmd: CreateUniverseCmd): Universe? = runBlocking {
        val response = HttpClient().get<HttpResponse>("$baseEndpoint/universes")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)

        val allUniverses: List<Universe> = mapper.readValue(response.readText(), object : TypeReference<List<Universe>>() {})
        allUniverses.find { it.name == createUniverseCmd.name && it.maxSize == createUniverseCmd.maxSize }
    }

    private fun createUniverse(createUniverseCmd: CreateUniverseCmd): Universe? = runBlocking {
        val response = HttpClient().post<HttpResponse> {
            body = mapper.writeValueAsString(createUniverseCmd)
            url(URL("$baseEndpoint/universes"))
        }

        assertThat(response.status).isEqualTo(HttpStatusCode.Created)

        val readText = response.readText()
        val universesFromPostResponse: List<Universe> = mapper.readValue(readText, object : TypeReference<List<Universe>>() {})
        universesFromPostResponse.find { it.name == createUniverseCmd.name && it.maxSize == createUniverseCmd.maxSize }
    }

}
