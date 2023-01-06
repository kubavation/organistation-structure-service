package com.durys.jakub.organistationstructure.domain

import com.durys.jakub.organistationstructure.commons.EventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrganisationStructureService(
        private val structureEntryRepository: StructureEntryRepository,
        private val eventPublisher: EventPublisher) {

    fun createStructure(parentId: String?, name: String, shortcut: String) {
        if (parentId != null) {
            addDependantStructure(parentId, name, shortcut)
        } else {
            structureEntryRepository.save(StructureEntry(UUID.randomUUID().toString(), name, shortcut))
        }
    }

    private fun addDependantStructure(parentId: String, name: String, shortcut: String) {
        val parent = structureEntryRepository.load(parentId) ?: throw StructureEntryNotFoundException(parentId)

        parent addDependant StructureEntry(UUID.randomUUID().toString(), name, shortcut)
        structureEntryRepository.save(parent)
    }

    fun changeStructureEntryDetails(structureEntryId: String, name: String, shortcut: String) {
        val entry = structureEntryRepository.load(structureEntryId) ?: throw StructureEntryNotFoundException(structureEntryId)

        entry.changeDetails(name, shortcut)
    }
}