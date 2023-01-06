package com.durys.jakub.organistationstructure.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("structure_entry")
class StructureEntry (
    @Id val id: String,
    var name: String,
    var shortcut: String) {

    enum class Status {
        ACTIVE, DEACTIVATED
    }

    @Field("entries")
    val entries: MutableList<StructureEntry> = mutableListOf()
    var status: Status = Status.ACTIVE
    var path: String = shortcut


    infix fun addDependant(dependant: StructureEntry) = entries.add(dependant withPathOf this)


    private infix fun withPathOf(parent: StructureEntry): StructureEntry {
        path = "${parent.path}/$shortcut"
        return this
    }

    fun deactivate() {
        status = Status.DEACTIVATED
    }

    fun changeDetails(name: String, shortcut: String) {
        this.name = name
        this.shortcut = shortcut
    }

}