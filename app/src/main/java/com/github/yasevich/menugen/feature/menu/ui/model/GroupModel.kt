package com.github.yasevich.menugen.feature.menu.ui.model

class GroupModel(
    val name: String,
    val description: String? = null,
) : MenuItemModel {
    override val key: String = "group/$name"
}
