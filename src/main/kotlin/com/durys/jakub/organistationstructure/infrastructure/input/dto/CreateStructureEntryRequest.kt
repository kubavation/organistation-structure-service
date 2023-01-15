package com.durys.jakub.organistationstructure.infrastructure.input.dto


class CreateStructureEntryRequest(val name: String, val shortcut: String) {
    constructor(): this( "","")
}