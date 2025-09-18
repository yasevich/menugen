package com.github.yasevich.menugen.feature.menu.ui.model

class GroupItem(
    val name: String,
    val description: String?,
    val price: String?,
) : MenuItemModel {
    override val key: String = "item/$name"
}
