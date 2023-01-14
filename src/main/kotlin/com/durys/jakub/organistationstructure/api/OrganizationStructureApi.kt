package com.durys.jakub.organistationstructure.api

import com.durys.jakub.organistationstructure.application.OrganizationStructureApplicationService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/organization-structure")
internal class OrganizationStructureApi(val organisationStructureService: OrganizationStructureApplicationService) {

    @GetMapping("/{structureId}")
    fun getStructure(@PathVariable structureId: String): StructureEntry {
        return organisationStructureService.findStructure(structureId);
    }
}