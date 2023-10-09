package com.durys.jakub.organistationstructure.domain.events

import com.durys.jakub.organistationstructure.commons.DomainEvent

class StructureEntryChanged(val structureId: String?, val name: String?,
                            val shortcut: String?, val path: String?): DomainEvent {
}