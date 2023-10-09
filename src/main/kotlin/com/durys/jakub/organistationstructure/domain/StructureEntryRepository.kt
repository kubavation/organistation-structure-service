package com.durys.jakub.organistationstructure.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StructureEntryRepository: JpaRepository<StructureEntry, String> {

    @Query("from StructureEntry e")
    fun load(id: String): StructureEntry? = null
    @Query("from StructureEntry e")
    fun loadEntryStructureByPath(path: String): StructureEntry? = null
    @Query("from StructureEntry e")
    fun save(entry: StructureEntry)
    @Query("from StructureEntry e")
    fun loadAll(): List<StructureEntry> = emptyList()
}