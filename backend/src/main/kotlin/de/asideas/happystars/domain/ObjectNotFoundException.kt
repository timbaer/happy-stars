package de.asideas.happystars.domain

import kotlin.reflect.KClass

class ObjectNotFoundException(id: String, type: KClass<Universe>) : RuntimeException("No such element of type $type for id $id")
