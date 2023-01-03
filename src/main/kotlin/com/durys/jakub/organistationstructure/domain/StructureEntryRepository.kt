package com.durys.jakub.organistationstructure.domain

interface StructureEntryRepository {
    fun load(id: String): StructureEntry
    fun loadByPath(path: String): StructureEntry
    fun save(entry: StructureEntry)
}