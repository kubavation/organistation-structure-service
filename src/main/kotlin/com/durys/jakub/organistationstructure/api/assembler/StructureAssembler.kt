package com.durys.jakub.organistationstructure.api.assembler

import com.durys.jakub.organistationstructure.api.model.StructureDTO
import com.durys.jakub.organistationstructure.domain.StructureEntry

internal object StructureAssembler {

    infix fun asDTO(entry: StructureEntry) = StructureDTO(entry.id, entry.name, entry.shortcut, entry.path)
}