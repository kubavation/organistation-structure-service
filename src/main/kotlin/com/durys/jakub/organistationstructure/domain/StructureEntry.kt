package com.durys.jakub.organistationstructure.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

internal fun String.parentPath(): String {
    val lastIndexOfParentSplitter = if (this.lastIndexOf("/") > 0) this.lastIndexOf("/") else 0
    return this.substring(0, lastIndexOfParentSplitter)
}

internal fun MutableList<StructureEntry>.addEntry(entry: StructureEntry): StructureEntry {
    add(entry)
    return entry
}


@Entity
class StructureEntry (

    @Id @GeneratedValue var id: String?,
    var name: String?,
    var shortcut: String?,
    @ManyToOne @JoinColumn(name = "parent_id") var parentStructure: StructureEntry?,
    @OneToMany(mappedBy = "parentStructure") var subStructures: MutableList<StructureEntry> = mutableListOf()) {

    constructor() : this(null, null, null, null, mutableListOf())

    enum class Status {
        ACTIVE, DEACTIVATED
    }

    var status: Status = Status.ACTIVE
    var path: String? = shortcut


    internal infix fun addDependant(dependant: StructureEntry): StructureEntry {
        return subStructures.addEntry(dependant withPathOf this)
    }

    fun dependantOfShortcut(shortcut: String): StructureEntry? {
        return subStructures.find { shortcut == it.shortcut }
    }


    private infix fun withPathOf(parent: StructureEntry): StructureEntry {
        path = "${parent.path}/$shortcut"
        return this
    }

    internal fun deactivate() {
        status = Status.DEACTIVATED
        subStructures.forEach {
            it.deactivate()
        }
    }

    internal fun changeDetails(name: String, shortcut: String) {
        this.name = name
        this.shortcut = shortcut

        val parentPath = this.path!!.parentPath()
        this.path = if (parentPath.isNotEmpty()) "${parentPath}/${shortcut}" else shortcut
        changeDependantsPath(this.path!!, this.subStructures)
    }

    private fun changeDependantsPath(parentPath: String, entries: MutableList<StructureEntry>) {
        entries.forEach {
            it.path = "${parentPath}/${it.shortcut}"
            it.changeDependantsPath(it.path!!, it.subStructures)
        }
    }


}