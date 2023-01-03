package com.durys.jakub.organistationstructure.infrastructure.input

import com.durys.jakub.organistationstructure.application.OrganizationStructure
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/")
class OrganisationStructureController(private val organisationStructure: OrganizationStructure) {
}