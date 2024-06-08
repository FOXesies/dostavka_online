package com.wayplaner.learn_room.admin.menu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.menu.presentation.components.AddProductView
import com.wayplaner.learn_room.admin.menu.presentation.components.CategoryAdminView
import com.wayplaner.learn_room.admin.menu.util.UiEventMenuAdd
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun MenuAddScreen(
    idProduct: Long?,
    category: String?,
    navController: NavController,
    modelView: MenuModelView = hiltViewModel()
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backHome)){

            LaunchedEffect(Unit) {
                modelView.onEvent(UiEventMenuAdd.GetCategories)
            }
        if(idProduct == null) {
            val result = modelView.responseProduct.observeAsState()
            MenuExist(modelView, result.value, "category")

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                text = "Добавление блюда",
                fontSize = 22.sp,
                color = whiteColor,
                fontFamily = FontFamily(
                    Font(
                        R.font.ag_cooper_cyr,
                        FontWeight.Medium
                    )
                ),
                textAlign = TextAlign.Center
            )
        }
        else{
            LaunchedEffect(Unit) {
                modelView.onEvent(UiEventMenuAdd.PickProduct(idProduct))
            }

            val product = modelView.responseProduct
            if(product.value != null)
                MenuExist(modelView, product.value, category)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                text = "Изменение блюда",
                fontSize = 22.sp,
                color = whiteColor,
                fontFamily = FontFamily(
                    Font(
                        R.font.ag_cooper_cyr,
                        FontWeight.Medium
                    )
                ),
                textAlign = TextAlign.Center
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .padding(top = 12.dp, start = 16.dp, bottom = 5.dp, end = 5.dp)
                .size(45.dp),
            containerColor = summRedColor,
            onClick = { navController.navigateUp() }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                tint = whiteColor,
                modifier = Modifier.size(32.dp),
                contentDescription = "Добавить"
            )
        }
    }
}

@Composable
private fun MenuExist(modelView: MenuModelView, result: Product?, category: String?){
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())){
        Text(text = "Категория блюда", fontSize = 16.sp, color = grayList, modifier = Modifier
            .padding(start = 20.dp, bottom = 2.dp)
            .padding(top = 80.dp))
        CategoryAdminView(modelView, category?: "")

        Spacer(modifier = Modifier.height(15.dp))

        if(result != null){
            AddProductView(
                result.idProduct,
                modelView,
                result.name,
                result.description?: "",
                result.weight,
                result.price!!,
                result.images
            )
        }
        else{
            AddProductView(
                null,
                modelView,
               "",
                "",
                null,
                0.0,
                null)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Menu_f(){

}