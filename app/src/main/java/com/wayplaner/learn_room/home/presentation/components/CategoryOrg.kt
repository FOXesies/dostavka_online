package com.wayplaner.learn_room.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.home.domain.model.CategoryDTO
import com.wayplaner.learn_room.home.presentation.MainModelView
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.textFieldBack

@Composable
fun CategoryOrg(
    category: CategoryDTO,
    homeViewModel: MainModelView,
    checkable_pick: MutableState<Boolean>
) {
    val pickedCategory = remember { mutableStateOf(checkable_pick.value) }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(14.dp),
        colors = CardDefaults.cardColors(grayColor),
        modifier = Modifier
            .toggleable(value = pickedCategory.value, onValueChange = {
                pickedCategory.value = it
            })
            .clickable {
                if (pickedCategory.value)
                    homeViewModel.removeCategoryPick(category)
                else
                    homeViewModel.addCategoryPick(category)
            }
            .padding(2.dp),
        shape = MaterialTheme.shapes.small,
    ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 10.dp
                    ),
                text = category.name,
                fontSize = 14.sp,
                color = textFieldBack,
                style = MaterialTheme.typography.displayLarge
            )
    }
}


@Composable
fun CategoryList(categories: List<CategoryDTO>?, homeViewModel: MainModelView){
    val lazyListState = rememberLazyListState()
    var checkable_pick = remember { mutableStateOf(homeViewModel.checkPickCategory()) }

    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) ){
        items(categories!!){category ->
            CategoryOrg(category, homeViewModel, checkable_pick)
        }
    }
}


@Preview(name = "Preview1", device = "id:pixel_xl", showSystemUi = true)
@Composable
fun Preview5() {


}