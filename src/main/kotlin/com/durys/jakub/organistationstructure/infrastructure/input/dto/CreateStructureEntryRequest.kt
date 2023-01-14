package com.durys.jakub.organistationstructure.infrastructure.input.dto


class CreateStructureEntryRequest(val parentId: String?, val name: String, val shortcut: String) {
    constructor(): this(null, "","")
}