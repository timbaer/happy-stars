package de.asideas.happystars.stars

import de.asideas.happystars.CreateStarCmd
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StarsServiceTest {

    @Test
    fun create() {
        val starsService = StarsService()
        starsService.create(CreateStarCmd("kype",87834,"kjlkd879"))
    }
}