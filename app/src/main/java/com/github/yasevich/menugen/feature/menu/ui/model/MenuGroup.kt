package com.github.yasevich.menugen.feature.menu.ui.model

class MenuGroup(
    val name: String,
    val description: String? = null,
) : MenuItemModel {

    override val key: String
        get() = name
}
