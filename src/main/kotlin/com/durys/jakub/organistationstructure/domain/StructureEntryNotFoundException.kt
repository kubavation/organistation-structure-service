package com.durys.jakub.organistationstructure.domain

class StructureEntryNotFoundException(val path: String) : RuntimeException("Structure entry of path $path not found") {
}