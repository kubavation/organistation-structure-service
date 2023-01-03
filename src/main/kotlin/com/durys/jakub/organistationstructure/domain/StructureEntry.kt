package com.durys.jakub.organistationstructure.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document("structure_entry")
class StructureEntry (
    @Id val id: String,
    val name: String,
    val shortcut: String) {

    @Field("entries")
    val entries: List<StructureEntry> = Collections.emptyList()
    val status: String = ""
    val path: String = ""

}