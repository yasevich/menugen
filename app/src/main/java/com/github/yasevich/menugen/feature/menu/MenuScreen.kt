package com.github.yasevich.menugen.feature.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.github.yasevich.menugen.feature.menu.ui.model.MenuGroup
import com.github.yasevich.menugen.feature.menu.ui.model.MenuItem
import com.github.yasevich.menugen.feature.menu.ui.model.MenuItemModel
import com.github.yasevich.menugen.ui.theme.Dimen
import com.github.yasevich.menugen.ui.theme.MenuGenTheme

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    MenuContent(viewModel.items, modifier.fillMaxSize())
}

@Composable
private fun MenuContent(
    items: List<MenuItemModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = Dimen.smallPadding),
        verticalArrangement = Arrangement.spacedBy(space = Dimen.smallPadding),
    ) {
        items(items, { it.key }) { item ->
            when (item) {
                is MenuGroup -> MenuGroupContent(group = item)
                is MenuItem -> MenuItemContent(item = item)
            }
        }
    }
}

@Composable
private fun MenuGroupContent(
    group: MenuGroup,
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
    item: MenuItem,
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
                Text(
                    text = item.description,
                )
            }
            Text(text = item.price, style = MaterialTheme.typography.titleMedium)
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
