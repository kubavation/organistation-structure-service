package com.durys.jakub.organistationstructure.domain

class StructureEntryNotFoundException(val id: String) : RuntimeException("Structure entry of id $id not found") {
}