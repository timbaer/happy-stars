package de.asideas.happystars.domain

class Universe (var id: Int, var name: String, var maxSize: Int? = 10) {

    companion object {
        val TABLE_NAME = "universes"
    }

}
