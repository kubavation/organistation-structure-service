package com.durys.jakub.organistationstructure.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


internal fun String.parentPath(): String {

    val lastIndexOfParentSplitter = if (this.lastIndexOf("/") > 0) this.lastIndexOf("/") else 0
    return this.substring(0, lastIndexOfParentSplitter)
}

internal fun MutableList<StructureEntry>.addDependant(entry: StructureEntry): StructureEntry {
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


    internal infix fun addDependant(dependant: StructureEntry): StructureEntry {
        return entries.addDependant(dependant withPathOf this)
    }

    fun dependantOfShortcut(shortcut: String): StructureEntry? {
        return entries.find { shortcut == it.shortcut }
    }


    private infix fun withPathOf(parent: StructureEntry): StructureEntry {
        path = "${parent.path}/$shortcut"
        return this
    }

    internal fun deactivate() {
        status = Status.DEACTIVATED
        entries.forEach {
            it.deactivate()
        }
    }

    internal fun changeDetails(name: String, shortcut: String) {
        this.name = name
        this.shortcut = shortcut

        val parentPath = this.path.parentPath()
        this.path = if (parentPath.isNotEmpty()) "${parentPath}/${shortcut}" else shortcut
        changeDependantsPath(this.path, this.entries)
    }

    private fun changeDependantsPath(parentPath: String, entries: MutableList<StructureEntry>) {
        entries.forEach {
            it.path = "${parentPath}/${it.shortcut}"
            it.changeDependantsPath(it.path, it.entries)
        }
    }


}