package com.github.yasevich.menugen.model

interface MenuGroup {
    val name: String
    val description: String?
    val items: List<MenuItem>
}
