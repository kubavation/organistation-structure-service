package com.durys.jakub.organistationstructure.infrastructure.input

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.infrastructure.input.dto.OrganizationStructureRepresentation
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class OrganizationStructureRepresentationAssembler
    : RepresentationModelAssembler<StructureEntry, OrganizationStructureRepresentation> {

    override fun toModel(entity: StructureEntry): OrganizationStructureRepresentation {
        val representation = OrganizationStructureRepresentation(entity.id, entity.name, entity.shortcut, entity.path)

        representation.add(linkTo<OrganizationStructureController> { getStructureDependants(entity.id) }
                .withRel("structure-dependants"))

        return representation
    }

    fun toModel(entities: List<StructureEntry>): List<OrganizationStructureRepresentation> {
        return entities.map { toModel(it) }
    }
}