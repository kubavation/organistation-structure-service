package com.durys.jakub.organistationstructure.infrastructure.output

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import org.springframework.data.mongodb.core.MongoTemplate

class MongoStructureEntryRepository(private val mongoTemplate: MongoTemplate): StructureEntryRepository {

    override fun load(id: String): StructureEntry {
        TODO("Not yet implemented")
    }

    override fun loadByPath(path: String): StructureEntry {
        TODO("Not yet implemented")
    }

    override fun save(entry: StructureEntry) {
        TODO("Not yet implemented")
    }
}