package de.asideas.happystars

data class CreateUniverseCmd(var name: String, var maxSize: Int)

data class Star(val name: String, val id: Int, val universeId: Int, val color: String)
data class CreateStarCmd(val name: String, val universeId: Int, val color: String)
