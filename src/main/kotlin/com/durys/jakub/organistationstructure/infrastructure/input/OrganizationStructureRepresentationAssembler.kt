package com.durys.jakub.organistationstructure.infrastructure.input

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.infrastructure.input.dto.OrganizationStructureRepresentation
import org.springframework.hateoas.server.RepresentationModelAssembler

class OrganizationStructureRepresentationAssembler
    : RepresentationModelAssembler<StructureEntry, OrganizationStructureRepresentation> {

    override fun toModel(entity: StructureEntry): OrganizationStructureRepresentation {
        TODO("Not yet implemented")
    }
}