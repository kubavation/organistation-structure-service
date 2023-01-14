package com.durys.jakub.organistationstructure.infrastructure.input.dto

import org.springframework.hateoas.RepresentationModel

open class OrganizationStructureRepresentation(val id: String, val name: String, val shortcut: String, val path: String)
    : RepresentationModel<OrganizationStructureRepresentation>()