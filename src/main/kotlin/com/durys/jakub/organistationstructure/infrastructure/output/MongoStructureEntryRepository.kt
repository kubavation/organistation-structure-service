package com.durys.jakub.organistationstructure.infrastructure.output

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo

open class MongoStructureEntryRepository(private val mongoTemplate: MongoTemplate): StructureEntryRepository {

    override fun load(id: String): StructureEntry? {
        return mongoTemplate.findOne(Query().addCriteria(StructureEntry::id isEqualTo id), StructureEntry::class.java)
    }

    override fun loadEntryStructureByPath(path: String): StructureEntry? {
        return mongoTemplate.findOne(Query().addCriteria(StructureEntry::path isEqualTo path), StructureEntry::class.java)
    }

    override fun save(entry: StructureEntry) {
        mongoTemplate.save(entry)
    }

    override fun loadAll(): List<StructureEntry> {
        return mongoTemplate.findAll(StructureEntry::class.java)
    }
}