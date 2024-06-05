package com.wayplaner.learn_room.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.home.presentation.MainModelView
import com.wayplaner.learn_room.organization.domain.model.FiltercategoryOrg
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.remove_category_filter
import com.wayplaner.learn_room.ui.theme.textFieldFocus
import com.wayplaner.learn_room.ui.theme.whiteColor


@Composable
fun CategoryList(
    categories: List<String>?,
    flags: MutableList<Boolean>,
    homeViewModel: MainModelView,
    filtercategoryOrg: FiltercategoryOrg
) {
    val checkable_pick = remember { mutableStateListOf<String>() }
    val hasFilter = remember { mutableStateOf(filtercategoryOrg.categories.isNotEmpty()) }

    LaunchedEffect(Unit){
        checkable_pick.clear()
        homeViewModel.clearFilterIds()
    }

    LaunchedEffect(checkable_pick.toList()) {
        hasFilter.value = checkable_pick.isNotEmpty()
    }

    Column {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(categories!!) { index, item ->
                ChipItem(flags[index], item) {
                    flags[index] = !flags[index]
                    if (flags[index])
                        checkable_pick.add(item)
                    else
                        checkable_pick.remove(item)
                    invokeSearch(homeViewModel, filtercategoryOrg.copy(categories = checkable_pick))
                }
            }
        }

        if (hasFilter.value) {
            Text(
                text = "Очистить фильтр",
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                color = remove_category_filter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 6.dp)
                    .clickable {
                        categories!!.forEachIndexed { index, _ ->
                            flags[index] = false
                        }
                        checkable_pick.clear()
                        homeViewModel.clearFilterIds()
                    }
            )
        }
    }
}

fun invokeSearch(homeViewModel: MainModelView, filtercategoryOrg: FiltercategoryOrg) {
        homeViewModel.getOrganizationsByCategory(filtercategoryOrg)
}
@Composable
fun ChipItem(active: Boolean, text: String, onClick: () -> Unit) {
    AssistChip(
        onClick = {
            onClick()
             },
        modifier = Modifier.padding(horizontal = 4.dp),
        border = null,
        colors = if(active) AssistChipDefaults.assistChipColors(textFieldFocus)
                 else AssistChipDefaults.assistChipColors(grayList),
        shape = RoundedCornerShape(10.dp),
        label = {
            Text(text = text,
                modifier = Modifier
                    .padding(
                        horizontal = 6.dp,
                        vertical = 10.dp),
                fontSize = 14.sp,
                color = if(active) whiteColor
            else grayColor_Text)
        },
    )
}


@Preview(name = "Preview1", device = "id:pixel_xl", showSystemUi = true)
@Composable
fun Preview5() {


}