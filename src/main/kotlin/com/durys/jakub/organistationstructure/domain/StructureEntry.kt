package com.durys.jakub.organistationstructure.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


fun String.parentPath(): String {

    val lastIndexOfParentSplitter = if (this.lastIndexOf("/") > 0) this.lastIndexOf("/") else 0
    return this.substring(0, lastIndexOfParentSplitter)
}

fun MutableList<StructureEntry>.addDependant(entry: StructureEntry): StructureEntry {
    this.add(entry)
    return entry
}


@Document("structure_entry")
class StructureEntry (
    @Id val id: String,
    var name: String,
    var shortcut: String,
    var entries: MutableList<StructureEntry> = mutableListOf()) {

    enum class Status {
        ACTIVE, DEACTIVATED
    }

    var status: Status = Status.ACTIVE
    var path: String = shortcut


    infix fun addDependant(dependant: StructureEntry): StructureEntry {
        return entries.addDependant(dependant withPathOf this)
    }


    private infix fun withPathOf(parent: StructureEntry): StructureEntry {
        path = "${parent.path}/$shortcut"
        return this
    }

    fun deactivate() {
        status = Status.DEACTIVATED
        entries.forEach {
            it.deactivate()
        }
    }

    fun changeDetails(name: String, shortcut: String) {
        this.name = name
        this.shortcut = shortcut

        val parentPath = this.path.parentPath()
        this.path = if (parentPath.isNotEmpty()) "${parentPath}/${shortcut}" else shortcut
        entries.forEach {entry ->
            changeDetails(entry.name, entry.shortcut)
        }
    }


}