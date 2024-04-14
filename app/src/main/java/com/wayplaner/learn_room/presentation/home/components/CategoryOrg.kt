package com.wayplaner.learn_room.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.domain.model.CategoryDTO
import com.wayplaner.learn_room.presentation.home.MainModelView
import com.wayplaner.learn_room.ui.theme.categoryColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun CategoryOrg(
    category: CategoryDTO,
    homeViewModel: MainModelView,
    checkable_pick: MutableState<Boolean>
){
    var pickedCategory = remember { mutableStateOf(checkable_pick.value) }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .toggleable(value = pickedCategory.value, onValueChange = {
                pickedCategory.value = it
            })
            .clickable {
                if(pickedCategory.value)
                    homeViewModel.removeCategoryPick(category)
                else
                    homeViewModel.addCategoryPick(category)
            }
            .height(90.dp)
            .width(130.dp)
            .padding(2.dp),
        shape = MaterialTheme.shapes.small,
    ) {
        Box {
            val painter = // Placeholder drawable
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = category.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            error(R.drawable.kofe) // Placeholder drawable
                        })
                        .build()
                )
            if(pickedCategory.value) {
                Image(
                    painter = painter,
                    alpha = 0.2F,
                    colorFilter = ColorFilter.tint(Color.Gray),
                    contentDescription = "category",
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                    text = category.name,
                    fontSize = 13.sp,
                    color = whiteColor,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            else {
                Image(
                    painter = painter,
                    contentDescription = "category",
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                    text = category.name,
                    fontSize = 13.sp,
                    color = categoryColor,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
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