package com.durys.jakub.organistationstructure.api.assembler

import com.durys.jakub.organistationstructure.api.model.StructureDTO
import com.durys.jakub.organistationstructure.domain.StructureEntry

object StructureAssembler {

    fun toDTO(entry: StructureEntry) = StructureDTO(entry.id, entry.name, entry.shortcut, entry.path)
}