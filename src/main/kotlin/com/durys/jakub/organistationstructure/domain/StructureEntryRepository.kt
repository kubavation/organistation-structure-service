package com.durys.jakub.organistationstructure.domain

interface StructureEntryRepository {
    fun load(id: String): StructureEntry?
    fun loadEntryStructureByPath(path: String): StructureEntry?
    fun save(entry: StructureEntry)
    fun loadAll(): List<StructureEntry>;
}