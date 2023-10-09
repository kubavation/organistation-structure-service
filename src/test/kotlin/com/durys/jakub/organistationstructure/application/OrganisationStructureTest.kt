package com.durys.jakub.organistationstructure.application

import com.durys.jakub.organistationstructure.domain.OrganisationStructureService
import com.durys.jakub.organistationstructure.domain.StructureEntry
import com.durys.jakub.organistationstructure.domain.StructureEntryNotFoundException
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import com.durys.jakub.organistationstructure.domain.events.StructureEntryChanged
import com.durys.jakub.organistationstructure.domain.events.StructureEntryDeactivated
import com.durys.jakub.organistationstructure.infrastructure.output.RabbitmqEventPublisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.util.UUID

internal class OrganisationStructureTest {


     val structureEntryRepository: StructureEntryRepository = Mockito.mock(StructureEntryRepository::class.java)

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


        Mockito.`when`(structureEntryRepository.loadEntryStructureByPath("D1")).thenReturn(parent)
        organizationStructure.addStructure(parent.path, name, shortcut)

        Mockito.verify(structureEntryRepository, Mockito.times(1)).save(any());
        Assertions.assertEquals(1, parent.subStructures.size)
        Assertions.assertEquals(name, parent.subStructures[0].name)
    }

    @Test
    fun addStructureEntryToNotFoundParent_shouldThrowException() {
        val parentId = UUID.randomUUID().toString();
        val name = "General department"
        val shortcut = "GD"

        Mockito.`when`(structureEntryRepository.load(parentId)).thenReturn(null)
        Assertions.assertThrows(StructureEntryNotFoundException::class.java) { organizationStructure.addStructure(parentId, name, shortcut) }
    }

    @Test
    fun deactivateStructureEntry_shouldDeactivate() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD")

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)

        organizationStructure.deactivateStructure(entryId)

        Assertions.assertEquals(StructureEntry.Status.DEACTIVATED, entry.status)
    }

    @Test
    fun deactivateStructureEntry_shouldDeactivateAlsoDependantEntries() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD", null,
            subStructures = mutableListOf(
                StructureEntry(UUID.randomUUID().toString(), "Department 1", "DD 1"),
                StructureEntry(UUID.randomUUID().toString(), "Department 2", "DD 2")
            )
        );

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)
        organizationStructure.deactivateStructure(entryId)

        Assertions.assertEquals(StructureEntry.Status.DEACTIVATED, entry.status)
        entry.subStructures.forEach {
            Assertions.assertEquals(StructureEntry.Status.DEACTIVATED, it.status)
        }
    }



    //changing structure details

    @Test
    fun changeStructureEntryDetailsWithoutDependants_shouldSuccessfullyChangeDetails() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD")

        val changedName = "Not general Department";
        val changedShortcut = "NGD";
        val expectedPath = "NGD"

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)
        organizationStructure.changeStructureDetails(entryId, changedName, changedShortcut);

        Assertions.assertEquals(entry.shortcut, changedShortcut)
        Assertions.assertEquals(entry.name, changedName)
        Assertions.assertEquals(entry.path, expectedPath)
    }

    @Test
    fun changeStructureEntryDetailsWith1LevelDependants_shouldSuccessfullyChangeDetails() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD", null,
                subStructures = mutableListOf(
                    StructureEntry(UUID.randomUUID().toString(), "Department 1", "DD1")
                )
        )

        val changedName = "Not general Department";
        val changedShortcut = "NGD";
        val expectedPath = "NGD/DD1"

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)
        organizationStructure.changeStructureDetails(entryId, changedName, changedShortcut);

        Assertions.assertEquals(entry.subStructures[0].path, expectedPath)
    }

    @Test
    fun changeStructureEntryDetailsWith2LevelDependants_shouldSuccessfullyChangeDetails() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD", null,
                subStructures = mutableListOf(
                        StructureEntry(UUID.randomUUID().toString(), "Department 1", "DD1", null,
                                subStructures = mutableListOf(
                                        StructureEntry(UUID.randomUUID().toString(), "Dependant Department 2", "DD2")
                                ))
                )
        )

        val changedName = "Not general Department";
        val changedShortcut = "NGD";
        val expectedPath = "NGD/DD1/DD2"

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)
        organizationStructure.changeStructureDetails(entryId, changedName, changedShortcut);

        Assertions.assertEquals(entry.subStructures[0].subStructures[0].path, expectedPath)
    }


    //events

    @Test
    fun deactivateStructureEntry_shouldEmitStructureEntryDeactivatedEvent() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD")

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)

        organizationStructure.deactivateStructure(entryId)

        Mockito.verify(eventPublisher, Mockito.times(1)).publish(any<StructureEntryDeactivated>())
    }

    @Test
    fun changeStructureEntry_shouldEmitStructureChangedEvent() {
        val entryId = UUID.randomUUID().toString()
        val entry = StructureEntry(entryId, "General department", "GD")

        val changedName = "Not general Department";
        val changedShortcut = "NGD";

        Mockito.`when`(structureEntryRepository.load(entryId)).thenReturn(entry)

        organizationStructure.changeStructureDetails(entryId, changedName, changedShortcut)

        Mockito.verify(eventPublisher, Mockito.times(1)).publish(any<StructureEntryChanged>())
    }

}