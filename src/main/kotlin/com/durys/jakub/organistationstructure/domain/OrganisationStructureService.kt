package com.durys.jakub.organistationstructure.domain

import com.durys.jakub.organistationstructure.commons.EventPublisher
import com.durys.jakub.organistationstructure.domain.events.StructureEntryChanged
import com.durys.jakub.organistationstructure.domain.events.StructureEntryDeactivated
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class OrganisationStructureService(
        private val structureEntryRepository: StructureEntryRepository,
        private val eventPublisher: EventPublisher) {

    internal fun createStructure(parentPath: String?, name: String, shortcut: String) {
        if (parentPath != null) {
            addDependantStructure(parentPath, name, shortcut)
        } else {
            structureEntryRepository.save(StructureEntry(UUID.randomUUID().toString(), name, shortcut))
        }
    }


    internal fun changeStructureEntryDetails(structureEntryId: String, name: String, shortcut: String) {
        val entry = structureEntryRepository.load(structureEntryId) ?: throw StructureEntryNotFoundException(structureEntryId)
        entry.changeDetails(name, shortcut)

        structureEntryRepository.save(entry)
        eventPublisher.publish(StructureEntryChanged(structureEntryId, entry.name, entry.shortcut, entry.path))
    }


    internal fun deactivateStructure(structureEntryId: String) {
        val entry = structureEntryRepository.load(structureEntryId) ?: throw StructureEntryNotFoundException(structureEntryId)
        entry.deactivate()
        structureEntryRepository.save(entry)
        eventPublisher.publish(StructureEntryDeactivated(structureEntryId))
    }

    private fun addDependantStructure(parentPath: String, name: String, shortcut: String) {
        val parent = findByPath(parentPath) ?: throw StructureEntryNotFoundException(parentPath)

        parent addDependant StructureEntry(UUID.randomUUID().toString(), name, shortcut)
        structureEntryRepository.save(parent)
    }

    private fun findEntryStructure(path: String): StructureEntry? {
        return structureEntryRepository.loadEntryStructureByPath(path.split("-")[0])
    }

    fun findByPath(path: String): StructureEntry? {

        var entry = findEntryStructure(path)

        path.split("-").stream()
                .skip(1)
                .forEach {
                    entry = entry?.dependantOfShortcut(it)
                }

        return entry
    }
}