package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.UUID

@Component
class OrganizationStructure(private val structureEntryRepository: StructureEntryRepository) {



    fun addStructure(parentId: String?, name: String, shortcut: String) {
        if (parentId != null) {
            val parent = structureEntryRepository.load(parentId)
            if (parent == null) {
                throw RuntimeException()
            }

            parent.addDependant(StructureEntry(UUID.randomUUID().toString(), name, shortcut))
            structureEntryRepository.save(parent)
        }

        structureEntryRepository.save(StructureEntry(UUID.randomUUID().toString(), name, shortcut))
    }

}