package com.github.yasevich.menugen.navigation

import kotlinx.serialization.Serializable

@Serializable
class MenuDestination(
    val uri: String,
)

@Serializable
object UploadDestination
