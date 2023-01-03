package com.durys.jakub.organistationstructure.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("structure_entry")
class StructureEntry (
    @Id val id: String,
    val name: String,
    val shortcut: String) {

    @Field("entries")
    val entries: MutableList<StructureEntry> = mutableListOf()
    val status: String = ""
    val path: String = ""


    fun addDependant(dependant: StructureEntry) {
        entries.add(dependant)
    }

}