package com.durys.jakub.organistationstructure.domain.events

import com.durys.jakub.organistationstructure.commons.DomainEvent

class StructureEntryDeactivated(val structureId: String): DomainEvent {
}