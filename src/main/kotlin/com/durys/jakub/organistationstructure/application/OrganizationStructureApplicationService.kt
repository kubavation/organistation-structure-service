package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.OrganisationStructureService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryNotFoundException
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrganizationStructureApplicationService(
        private val structureEntryRepository: StructureEntryRepository,
        private val organisationStructureService: OrganisationStructureService) {

    fun addStructure(parentId: String?, name: String, shortcut: String) {
        organisationStructureService.createStructure(parentId, name, shortcut)
    }

    fun findStructure(id: String): StructureEntry {
       return structureEntryRepository.load(id) ?: throw StructureEntryNotFoundException(id)
    }


}