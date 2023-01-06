package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.commons.EventPublisher
import com.durys.jakub.organistationstructure.domain.OrganisationStructureService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryNotFoundException
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import com.durys.jakub.organistationstructure.infrastructure.output.MongoStructureEntryRepository
import com.durys.jakub.organistationstructure.infrastructure.output.RabbitmqEventPublisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.util.UUID

class OrganisationStructureTest {


     val structureEntryRepository: StructureEntryRepository = Mockito.mock(MongoStructureEntryRepository::class.java)

     val eventPublisher: RabbitmqEventPublisher = Mockito.mock(RabbitmqEventPublisher::class.java);

     val organizationStructureDomainService = OrganisationStructureService(structureEntryRepository, eventPublisher)

     val organizationStructure: OrganizationStructureApplicationService
                = OrganizationStructureApplicationService(structureEntryRepository, organizationStructureDomainService)


    @Test
    fun addStructureEntryWithoutParent_shouldSuccessfullyCreateEntry() {
        val parentId = null;
        val name = "General department"
        val shortcut = "GD"

        organizationStructure.addStructure(parentId, name, shortcut)

        Mockito.verify(structureEntryRepository, Mockito.times(1)).save(any());
    }

    @Test
    fun addStructureEntryToParent_shouldSuccessfullyCreateEntry() {
        val parentId = UUID.randomUUID().toString();
        val name = "General department"
        val shortcut = "GD"
        val parent = StructureEntry(parentId, "Department", "D1");


        Mockito.`when`(structureEntryRepository.load(parentId)).thenReturn(parent)
        organizationStructure.addStructure(parentId, name, shortcut)

        Mockito.verify(structureEntryRepository, Mockito.times(1)).save(any());
        Assertions.assertEquals(1, parent.entries.size)
        Assertions.assertEquals(name, parent.entries[0].name)
    }

    @Test
    fun addStructureEntryToNotFoundParent_shouldThrowException() {
        val parentId = UUID.randomUUID().toString();
        val name = "General department"
        val shortcut = "GD"

        Mockito.`when`(structureEntryRepository.load(parentId)).thenReturn(null)
        Assertions.assertThrows(StructureEntryNotFoundException::class.java) { organizationStructure.addStructure(parentId, name, shortcut) }

    }

}