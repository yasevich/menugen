package com.github.yasevich.menugen.data.openai.model

import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.github.yasevich.menugen.model.MenuItem

class OpenAiFoodItem(
    @JvmField
    @field:JsonPropertyDescription("Name of an item.")
    var name: String,
    @JvmField
    @field:JsonPropertyDescription("Description of an item. May contain ingredients and additions.")
    var description: String?,
    @JvmField
    @field:JsonPropertyDescription("The price for an item without any additions in a local currency.")
    var price: String?,
)

fun OpenAiFoodItem.asFoodItem(): MenuItem {
    return object : MenuItem {
        override val name: String
            get() = this@asFoodItem.name
        override val description: String?
            get() = this@asFoodItem.description
        override val price: String?
            get() = this@asFoodItem.price
    }
}
