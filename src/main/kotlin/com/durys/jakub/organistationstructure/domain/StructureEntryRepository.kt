package com.durys.jakub.organistationstructure.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StructureEntryRepository: JpaRepository<StructureEntry, String> {

    @Query("from StructureEntry e where e.id = :id")
    fun load(id: String): StructureEntry?
    @Query("from StructureEntry e where e.path = :path")
    fun loadEntryStructureByPath(path: String): StructureEntry?
    @Query("from StructureEntry e")
    fun loadAll(): List<StructureEntry> = emptyList()
}