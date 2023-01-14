package com.durys.jakub.organistationstructure.infrastructure.input

import com.durys.jakub.organistationstructure.application.OrganizationStructureApplicationService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.infrastructure.input.dto.CreateStructureEntryRequest
import com.durys.jakub.organistationstructure.infrastructure.input.dto.OrganizationStructureRepresentation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organization-structure")
internal class OrganizationStructureController(
        private val organisationStructure: OrganizationStructureApplicationService,
        private val assembler: OrganizationStructureRepresentationAssembler) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody structure: CreateStructureEntryRequest) = organisationStructure.addStructure(structure.parentId, structure.name, structure.shortcut);


    @GetMapping("/{structureId}")
    fun getStructure(@PathVariable structureId: String): OrganizationStructureRepresentation {
        return assembler.toModel(organisationStructure.findStructure(structureId))
    }


    @GetMapping("/{structureId}/dependants")
    fun getStructureDependants(@PathVariable structureId: String): List<OrganizationStructureRepresentation> {
        return assembler.toModel(organisationStructure.findDependants(structureId))
    }

    @GetMapping
    fun getOrganizationStructure(): List<OrganizationStructureRepresentation> {
        return assembler.toModel(organisationStructure.getOrganizationStructure())
    }

}