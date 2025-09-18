package com.github.yasevich.menugen.data.openai.model

import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.github.yasevich.menugen.model.MenuGroup
import com.github.yasevich.menugen.model.MenuItem

class OpenAiMenuGroup(
    @JvmField
    @field:JsonPropertyDescription("Name of a menu group.")
    var name: String,
    @JvmField
    @field:JsonPropertyDescription("Optional description of a menu group.")
    var description: String?,
    @JvmField
    var items: List<OpenAiFoodItem>,
)

fun OpenAiMenuGroup.asMenuGroup(): MenuGroup {
    return object : MenuGroup {
        override val name: String
            get() = this@asMenuGroup.name
        override val description: String?
            get() = this@asMenuGroup.description
        override val items: List<MenuItem>
            get() = this@asMenuGroup.items.map(OpenAiFoodItem::asFoodItem)
    }
}
