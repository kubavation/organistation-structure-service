package com.durys.jakub.organistationstructure.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

internal class StructureEntryTest {


    @Test
    fun createStructureEntry_shouldContainsPathWithShortcutOnly() {
        val entryId = UUID.randomUUID().toString()
        val name = "General Department"
        val shortcut = "GD"
        val expectedPath = "GD"

        val entry = StructureEntry(entryId, name, shortcut)

        assertEquals(entry.path, expectedPath)
    }

    @Test
    fun createDependantStructureEntry_shouldContainsPathWith1LevelParentShortcut() {
        val entry = StructureEntry(UUID.randomUUID().toString(), "General Department", "GD")
        val expectedPath = "GD\\DD1"

        val dependant_1  = entry.addDependant(StructureEntry(UUID.randomUUID().toString(), "Dependant department", "DD1"))

        assertEquals(1, entry.entries.size)
        assertEquals(dependant_1.path, expectedPath)
    }

    @Test
    fun createDependantStructureEntry_shouldContainsPathWith2LevelParentsShortcut() {
        val entry = StructureEntry(UUID.randomUUID().toString(), "General Department", "GD")
        val expectedPath = "GD\\DD1\\DD2"

        val dependant_1 = entry.addDependant(StructureEntry(UUID.randomUUID().toString(), "Dependant department", "DD1"))

        val dependant_2 = dependant_1.addDependant(StructureEntry(UUID.randomUUID().toString(), "Dependant department 2", "DD2"))


        assertEquals(1, dependant_1.entries.size)
        assertEquals(dependant_2.path, expectedPath)
    }
}