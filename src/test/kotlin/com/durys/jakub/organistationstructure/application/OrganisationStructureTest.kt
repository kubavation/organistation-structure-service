package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryNotFoundException
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import com.durys.jakub.organistationstructure.infrastructure.output.MongoStructureEntryRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.util.UUID

class OrganisationStructureTest {


     val structureEntryRepository: StructureEntryRepository = Mockito.mock(MongoStructureEntryRepository::class.java)

     val organisationStructure: OrganizationStructure = OrganizationStructure(structureEntryRepository)


    @Test
    fun addStructureEntryWithoutParent_shouldSuccessfullyCreateEntry() {
        val parentId = null;
        val name = "General department"
        val shortcut = "GD"

        organisationStructure.addStructure(parentId, name, shortcut)

        Mockito.verify(structureEntryRepository, Mockito.times(1)).save(any());
    }

    @Test
    fun addStructureEntryToParent_shouldSuccessfullyCreateEntry() {
        val parentId = UUID.randomUUID().toString();
        val name = "General department"
        val shortcut = "GD"
        val parent = StructureEntry(parentId, "Department", "D1");


        Mockito.`when`(structureEntryRepository.load(parentId)).thenReturn(parent)
        organisationStructure.addStructure(parentId, name, shortcut)

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
        Assertions.assertThrows(StructureEntryNotFoundException::class.java) { organisationStructure.addStructure(parentId, name, shortcut) }

    }

}