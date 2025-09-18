package com.github.yasevich.menugen.data.openai.model

import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.github.yasevich.menugen.model.MenuGroup
import com.github.yasevich.menugen.model.RestaurantMenu

class OpenAiShopMenu(
    @JvmField
    @field:JsonPropertyDescription("List of groups of items in a menu.")
    val groups: List<OpenAiMenuGroup>
)

fun OpenAiShopMenu.asShopMenu(): RestaurantMenu {
    return object : RestaurantMenu {
        override val groups: List<MenuGroup>
            get() = this@asShopMenu.groups.map(OpenAiMenuGroup::asMenuGroup)
    }
}
