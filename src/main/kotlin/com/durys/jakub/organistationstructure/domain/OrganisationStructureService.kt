package com.durys.jakub.organistationstructure.domain

import com.durys.jakub.organistationstructure.commons.EventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrganisationStructureService(
        private val structureEntryRepository: StructureEntryRepository,
        private val eventPublisher: EventPublisher) {

    
}