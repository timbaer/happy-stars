package de.asideas.happystars.domain

class Star (var id: Int, var name: String, var universeId: Int, var color: String) {

    companion object {
        val TABLE_NAME = "star"
    }

}
