package com.github.yasevich.menugen.feature.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yasevich.menugen.feature.menu.ui.model.GroupItem
import com.github.yasevich.menugen.feature.menu.ui.model.GroupModel
import com.github.yasevich.menugen.ui.theme.Dimen
import com.github.yasevich.menugen.ui.theme.MenuGenTheme

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is MenuViewModel.UiState.Failure -> MenuFailure(state, modifier)
        is MenuViewModel.UiState.Loading -> MenuLoading(modifier)
        is MenuViewModel.UiState.Success -> MenuContent(state, modifier)
    }
}

@Composable
private fun MenuFailure(
    state: MenuViewModel.UiState.Failure,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = state.message,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun MenuLoading(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MenuContent(
    state: MenuViewModel.UiState.Success,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = Dimen.smallPadding),
        verticalArrangement = Arrangement.spacedBy(space = Dimen.smallPadding),
    ) {
        items(items = state.models, key = { it.key }) { item ->
            when (item) {
                is GroupModel -> MenuGroupContent(group = item)
                is GroupItem -> MenuItemContent(item = item)
            }
        }
    }
}

@Composable
private fun MenuGroupContent(
    group: GroupModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.screenHorizontalPadding)
            .padding(top = Dimen.mediumPadding)
    ) {
        Text(
            text = group.name,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimen.smallPadding))
        if (group.description != null) {
            Text(text = group.description, modifier = Modifier.padding(bottom = Dimen.smallPadding))
        }
    }
}

@Composable
private fun MenuItemContent(
    item: GroupItem,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.padding(horizontal = Dimen.screenHorizontalPadding),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                item.description?.let { description ->
                    Text(text = description)
                }
            }
            item.price?.let { price ->
                Text(text = price, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MenuScreenPreview() {
    MenuGenTheme {
        MenuScreen()
    }
}
