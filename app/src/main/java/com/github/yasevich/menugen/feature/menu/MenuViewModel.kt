package com.github.yasevich.menugen.feature.menu

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.yasevich.menugen.data.UploadRepository
import com.github.yasevich.menugen.feature.menu.ui.model.GroupItem
import com.github.yasevich.menugen.feature.menu.ui.model.GroupModel
import com.github.yasevich.menugen.feature.menu.ui.model.MenuItemModel
import com.github.yasevich.menugen.model.MenuGroup
import com.github.yasevich.menugen.model.MenuItem
import com.github.yasevich.menugen.model.RestaurantMenu
import com.github.yasevich.menugen.navigation.MenuDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    uploadRepository: UploadRepository,
) : ViewModel() {

    val uiState: StateFlow<UiState> =
        flow {
            uploadRepository
                .uploadMenu(savedStateHandle.toRoute<MenuDestination>().uri.toUri())
                .onSuccess { emit(it) }
                .onFailure { throw it }
        }
        .map { it.asSuccess() }
        .catch { emit(UiState.Failure(it.message ?: "unknown error")) } // todo handle errors
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UiState.Loading,
        )

    sealed interface UiState {
        object Loading : UiState
        class Success(val models: List<MenuItemModel>) : UiState
        class Failure(val message: String) : UiState
    }
}

private fun RestaurantMenu.asSuccess(): MenuViewModel.UiState {
    return MenuViewModel.UiState.Success(
        buildList {
            for (group in groups) {
                group.populateInto(this)
            }
        }
    )
}

private fun MenuGroup.populateInto(models: MutableList<MenuItemModel>) {
    models += GroupModel(
        name = name,
        description = description,
    )

    items.forEach { item ->
        item.populateInto(models)
    }
}

private fun MenuItem.populateInto(models: MutableList<MenuItemModel>) {
    models += GroupItem(
        name = name,
        description = description,
        price = price
    )
}
