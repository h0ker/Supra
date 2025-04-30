package com.hoker.supraexample.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraTitleTextMedium
import com.hoker.supraexample.domain.models.NavRoute

@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    optionsList: List<NavRoute>,
    onOptionSelected: (NavRoute) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(optionsList) { optionRoute ->
            SupraTitleTextMedium(
                modifier = Modifier
                    .padding(Sizes.small)
                    .clickable {
                        onOptionSelected(optionRoute)
                        closeDrawer()
                    },
                text = optionRoute.pageTitle
            )
        }
    }
}