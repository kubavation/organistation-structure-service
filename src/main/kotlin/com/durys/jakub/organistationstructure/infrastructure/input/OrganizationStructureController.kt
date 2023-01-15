package com.durys.jakub.organistationstructure.infrastructure.input

import com.durys.jakub.organistationstructure.application.OrganizationStructureApplicationService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.infrastructure.input.dto.CreateStructureEntryRequest
import com.durys.jakub.organistationstructure.infrastructure.input.dto.OrganizationStructureRepresentation
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.mediatype.hal.HalModelBuilder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/organization-structure")
internal class OrganizationStructureController(
        private val organisationStructure: OrganizationStructureApplicationService,
        private val assembler: OrganizationStructureRepresentationAssembler) {


    @PostMapping(value = ["", "/{path}"])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable path: String?, @RequestBody structure: CreateStructureEntryRequest) {
        organisationStructure.addStructure(path, structure.name, structure.shortcut)
    }


    @GetMapping("/{path}")
    fun getStructure(@PathVariable path: String): OrganizationStructureRepresentation {
        return assembler.toModel(organisationStructure.findStructure(path))
    }

    @GetMapping("/{path}/dependants")
    fun getStructureDependants(@PathVariable path: String): List<OrganizationStructureRepresentation> {
        return assembler.toModel(organisationStructure.findDependants(path))
    }

    @GetMapping
    fun getOrganizationStructure(): List<OrganizationStructureRepresentation> {
        return assembler.toModel(organisationStructure.getOrganizationStructure())
    }

}