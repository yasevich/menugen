package com.github.yasevich.menugen.data

import android.net.Uri
import com.github.yasevich.menugen.model.RestaurantMenu

interface UploadRepository {
    suspend fun uploadMenu(uri: Uri): Result<RestaurantMenu>
}
