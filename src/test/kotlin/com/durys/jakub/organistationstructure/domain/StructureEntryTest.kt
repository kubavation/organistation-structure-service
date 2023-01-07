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
        val expectedPath = "GD/DD1"

        entry.addDependant(StructureEntry(UUID.randomUUID().toString(), "Dependant department", "DD1"))

        assertEquals(1, entry.entries.size)
        assertEquals(entry.entries[0].path, expectedPath)
    }
}