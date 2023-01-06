package com.durys.jakub.organistationstructure.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("structure_entry")
class StructureEntry (
    @Id val id: String,
    val name: String,
    val shortcut: String) {

    enum class Status {
        ACTIVE, DEACTIVATED
    }

    @Field("entries")
    val entries: MutableList<StructureEntry> = mutableListOf()
    var status: Status = Status.ACTIVE
    var path: String? = shortcut


    fun addDependant(dependant: StructureEntry) {
        preparePath(dependant)
        entries.add(dependant)
    }

    private fun withPath(parent: StructureEntry) {
        path = "${parent.path}/$shortcut"
    }

    fun deactivate() {
        status = Status.DEACTIVATED
    }

}