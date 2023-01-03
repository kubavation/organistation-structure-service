package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import com.durys.jakub.organistationstructure.infrastructure.output.MongoStructureEntryRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.UUID

class OrganisationStructureTest {


     val structureEntryRepository: StructureEntryRepository = Mockito.mock(MongoStructureEntryRepository::class.java)

     val organisationStructure: OrganizationStructure = OrganizationStructure(structureEntryRepository)


    @Test
    fun addStructureEntryWithoutParent_shouldSuccessfullyCreateEntry() {
        val parentId = UUID.randomUUID().toString()
        val name = "General department"
        val shortcut = "GD"

        organisationStructure.addStructure(parentId, name, shortcut)

        Mockito.verify(structureEntryRepository, Mockito.times(1)).save(Mockito.any(StructureEntry::class.java));
    }

}