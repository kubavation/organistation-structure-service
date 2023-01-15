package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.OrganisationStructureService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryNotFoundException
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
internal class OrganizationStructureApplicationService(
        private val structureEntryRepository: StructureEntryRepository,
        private val organisationStructureService: OrganisationStructureService) {

    fun addStructure(parentPath: String?, name: String, shortcut: String) {
        organisationStructureService.createStructure(parentPath, name, shortcut)
    }

    fun changeStructureDetails(structureEntryId: String, name: String, shortcut: String) {
        organisationStructureService.changeStructureEntryDetails(structureEntryId, name, shortcut)
    }

    fun deactivateStructure(structureEntryId: String) {
        organisationStructureService.deactivateStructure(structureEntryId)
    }

    fun findStructure(id: String): StructureEntry {
        return structureEntryRepository.load(id) ?: throw StructureEntryNotFoundException(id)
    }

    fun findDependants(path: String): MutableList<StructureEntry> {
        return organisationStructureService.findByPath(path)?.entries ?: mutableListOf()
    }

    fun getOrganizationStructure(): List<StructureEntry> {
        return structureEntryRepository.loadAll()
    }

}