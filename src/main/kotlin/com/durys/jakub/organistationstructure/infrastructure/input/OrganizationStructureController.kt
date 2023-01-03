package com.durys.jakub.organistationstructure.infrastructure.input

import com.durys.jakub.organistationstructure.application.OrganizationStructure
import com.durys.jakub.organistationstructure.infrastructure.input.dto.StructureEntryDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class OrganizationStructureController(private val organisationStructure: OrganizationStructure) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody structure: StructureEntryDTO) {
        organisationStructure.addStructure(structure.parentId, structure.name, structure.shortcut);
    }
}