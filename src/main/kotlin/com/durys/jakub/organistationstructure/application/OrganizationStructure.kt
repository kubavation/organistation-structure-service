package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrganizationStructure(private val structureEntryRepository: StructureEntryRepository) {


    fun addStructure(parentId: String?, name: String, shortcut: String) {
        if (parentId != null) {
            //todo
        }

        structureEntryRepository.save(StructureEntry(UUID.randomUUID().toString(), name, shortcut))
    }

}