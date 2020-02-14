package de.asideas.happystars.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@RegisterForReflection
class Universe {

    var id: Int
    var name: String
    var maxSize: Int

    @JsonCreator(mode = JsonCreator.Mode.DEFAULT)
    constructor(@JsonProperty("id") id: Int,@JsonProperty("name")  name: String,@JsonProperty("maxSize")  maxSize: Int) {
        this.id = id
        this.name = name
        this.maxSize = maxSize
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Universe

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }


//
//    constructor() {
//        id = 0
//        name = ""
//        maxSize = 0
//    }

}
