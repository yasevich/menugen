package com.github.yasevich.menugen.feature.menu.ui.model

class MenuItem(
    val name: String,
    val description: String,
    val price: String,
) : MenuItemModel {

    override val key: String
        get() = name
}
