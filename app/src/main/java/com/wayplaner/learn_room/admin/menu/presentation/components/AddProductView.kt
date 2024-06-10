package com.wayplaner.learn_room.admin.menu.presentation.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.menu.presentation.MenuModelView
import com.wayplaner.learn_room.admin.menu.util.UiEventMenuAdd
import com.wayplaner.learn_room.admin.util.toBitmapImage
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.redLogoColor
import com.wayplaner.learn_room.ui.theme.whiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddProductView(
    id: Long?,
    modelView: MenuModelView,
    name: String,
    description: String,
    weihth: Float?,
    price: Double?,
    image: List<Image>?,
    navController: NavController
) {
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        focusedTextColor = whiteColor,
        cursorColor = whiteColor,
        unfocusedTextColor = grayList,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent)

    var nameValue by remember { mutableStateOf(name) }
    var descriptionValue by remember { mutableStateOf(description) }
    var weigth by remember { mutableStateOf(weihth?.toString()?: "") }
    var price by remember { mutableStateOf(price?.toString()?: "") }

    val context = LocalContext.current

    if(modelView.back.observeAsState().value != null){
        navController.navigateUp()
    }

    if(modelView.errorMessage.observeAsState().value != null){
        Toast.makeText(context, modelView.errorMessage.value, Toast.LENGTH_LONG).show()
    }

    val selectImages = remember { mutableStateListOf<Image>() }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
            val byteArray = inputStream?.readBytes()
            if(byteArray != null) {
                if(selectImages.size == 0) selectImages.add(Image(value = byteArray))
                else selectImages.add(Image(value = byteArray))
            }
        }

    Column() {

        LaunchedEffect(Unit){
            if(image != null)
                selectImages.addAll(image)
        }

        val coroutineScope = rememberCoroutineScope()

        if(selectImages.size == 0){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.no_fof),
                contentDescription = null
            )
        }
        else {
            val state = rememberPagerState {
                selectImages.size
            }

            HorizontalPager(
                state = state, modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth()
            ) { page ->

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(contentAlignment = Alignment.BottomCenter) {
                        if(selectImages[page].value != null) {
                            Image(
                                bitmap = selectImages[page].value!!.toBitmapImage(),
                                contentDescription = "",
                                Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            var color = remember {
                                mutableStateOf(
                                    if (selectImages[page]!!.main) redActionColor else backHeader.copy(
                                        alpha = 0.3f
                                    )
                                )
                            }

                            FloatingActionButton(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .clip(MaterialTheme.shapes.small)
                                    .padding(bottom = 8.dp, end = 10.dp)
                                    .size(45.dp),
                                containerColor = color.value,
                                onClick = {
                                    selectImages.forEach { it.main = false }
                                    selectImages[page].main = true
                                    color.value =
                                        if (selectImages[page]!!.main) redActionColor else backHeader.copy(
                                            alpha = 0.5f
                                        )
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.home_image),
                                    tint = if (selectImages[page]!!.main) whiteColor else grayList,
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Удалить"
                                )
                            }

                            FloatingActionButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clip(MaterialTheme.shapes.small)
                                    .padding(top = 8.dp, end = 10.dp)
                                    .size(45.dp),
                                containerColor = backHeader,
                                onClick = {
                                    coroutineScope.launch {
                                        if (selectImages.size != 1) {
                                            if (page == 0)
                                                state.animateScrollToPage(page.plus(1))
                                            else
                                                state.animateScrollToPage(page.minus(1))

                                            selectImages.removeAt(page)
                                        } else
                                            selectImages.removeAt(page)
                                    }
                                }) {
                                Image(
                                    painter = painterResource(id = R.drawable.trash_can_115312),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = "Главная фотография"
                                )
                            }
                        }
                    }
                }
            }
        }


        Column(modifier = Modifier.padding(horizontal = 30.dp)) {

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(
                    text = "Добавить фото блюда",
                    fontSize = 15.sp,
                    color = grayList
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Название блюда", fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp),
                color = grayList
            )


            Spacer(modifier = Modifier.height(2.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp)) {
                TextField(
                    value = nameValue,
                    onValueChange = {
                        nameValue = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20)),
                    colors = colorET
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Описание блюда", fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp),
                color = grayList
            )

            Spacer(modifier = Modifier.height(2.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp)) {
                TextField(
                    value = descriptionValue,
                    onValueChange = {
                        descriptionValue = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20)),
                    colors = colorET
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Вес блюда в граммах", fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp),
                color = grayList
            )

            Spacer(modifier = Modifier.height(2.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp)) {
                TextField(
                    value = weigth,
                    onValueChange = {
                        try {
                            it.toFloat()
                            weigth = it
                        } catch (nfe: NumberFormatException) {
                            if(it.length == 0)
                                weigth = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20)),
                    colors = colorET
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Цена в рублях", fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp),
                color = grayList
            )

            Spacer(modifier = Modifier.height(2.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp)) {
                TextField(
                    value = price,
                    onValueChange = {
                        try {
                            it.toDouble()
                            price = it
                        } catch (nfe: NumberFormatException) {
                            if(it.length == 0)
                                price = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20)),
                    colors = colorET
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                          //modelView.onEvent(UiEventMenuAdd.UpdateImage(context, selectImages[0].value!!))
                    modelView.onEvent(UiEventMenuAdd.UpdateProduct(
                        id,
                        nameValue,
                        descriptionValue,
                        if(price.isEmpty()) null else price.toDouble(),
                        if(weigth.isEmpty()) null else weigth.toFloat(),
                        selectImages,
                        context
                    ) )
                },
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Сохранить изменения", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

    }

}
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Menu1f(){
}