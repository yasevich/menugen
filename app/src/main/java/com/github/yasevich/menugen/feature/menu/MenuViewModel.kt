package com.github.yasevich.menugen.feature.menu

import androidx.lifecycle.ViewModel
import com.github.yasevich.menugen.feature.menu.ui.model.MenuItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    val items: List<MenuItemModel> = emptyList()
}
