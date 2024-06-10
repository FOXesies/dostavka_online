package com.wayplaner.learn_room.admin.basic_info.presentation

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MapSearchActivity
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.basic_info.domain.model.ImageDTO
import com.wayplaner.learn_room.admin.basic_info.util.UiEventBasicInfoA
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.admin.util.toBitmapImage
import com.wayplaner.learn_room.createorder.presentation.components.AddressSuggestModelView
import com.wayplaner.learn_room.organization.model.CityOrganization
import com.wayplaner.learn_room.ui.theme.adminAdressAddCity
import com.wayplaner.learn_room.ui.theme.adminAdressCity
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicInfo(
    navController: NavController,
    vmBasic: BasicInfoModelView = hiltViewModel()) {

    val context = LocalContext.current
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        focusedTextColor = whiteColor,
        cursorColor = whiteColor,
        unfocusedTextColor = grayList,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )


    var nameValue by remember { mutableStateOf("") }
    var phoneValue by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var infoUpdate by remember { mutableStateOf(false) }
    val result = vmBasic.infoOrg.observeAsState()

        val resultFound = result.value


        val error = vmBasic.errorMessage.observeAsState()
        if (error.value != null) {
            Toast.makeText(context, error.value, Toast.LENGTH_LONG).show()
            vmBasic.errorMessage.value = null
        }


        val selectImages = remember { mutableStateListOf<ImageDTO>() }


        LaunchedEffect(resultFound) {
            if(resultFound != null) {
                if (resultFound.idImages != null)
                    selectImages.addAll(resultFound.idImages!!)
                vmBasic.addAllCity(resultFound.locationAll)

                nameValue = resultFound.name
                phoneValue = resultFound.phone.replace("+", "")
                description = resultFound.description?: ""
            }
        }

        if(vmBasic.back.observeAsState().value!!){
            navController.navigateUp()
        }

        val coroutineScope = rememberCoroutineScope()
        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
                val byteArray = inputStream?.readBytes()
                if (byteArray != null) {
                    if (selectImages.size == 0) selectImages.add(ImageDTO(byteArray = byteArray))
                    else selectImages.add(ImageDTO(byteArray = byteArray))
                }
            }

        Box(modifier = Modifier.fillMaxSize()
            .background(backHome)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                if (selectImages.size == 0) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.no_fof),
                        contentDescription = null
                    )
                } else {
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
                            if (selectImages[page].byteArray != null) {
                                Box(contentAlignment = Alignment.BottomCenter) {
                                    Image(
                                        bitmap = selectImages[page].byteArray!!.toBitmapImage(),
                                        contentDescription = "",
                                        Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )

                                    var color = remember {
                                        mutableStateOf(
                                            if (selectImages[page].main) redActionColor else backHeader.copy(
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
                                                if (selectImages[page].main) redActionColor else backHeader.copy(
                                                    alpha = 0.5f
                                                )
                                        }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.home_image),
                                            tint = if (selectImages[page].main) whiteColor else grayList,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .background(Color.Transparent),
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

                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .padding(horizontal = 25.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Добавить фото", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(30.dp))

                Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                    Text(
                        text = "Название ресторана", fontSize = 16.sp, color = grayList,
                        modifier = Modifier.padding(start = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(orderCreateBackField),
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        TextField(
                            value = nameValue,
                            onValueChange = {
                                nameValue = it
                                infoUpdate = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .clip(RoundedCornerShape(20)),
                            colors = colorET
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Номер телефона", fontSize = 16.sp, color = grayList,
                        modifier = Modifier.padding(start = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(orderCreateBackField),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        TextField(
                            value = phoneValue,
                            onValueChange = {
                                phoneValue = it
                                infoUpdate = true
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .clip(RoundedCornerShape(20)),
                            colors = colorET
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Описание ресторана", fontSize = 16.sp, color = grayList,
                        modifier = Modifier.padding(start = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(orderCreateBackField),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        TextField(
                            value = description ?: "",
                            onValueChange = {
                                description = it
                                infoUpdate = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .clip(RoundedCornerShape(20)),
                            colors = colorET
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Города ресторана", fontSize = 16.sp, color = grayList,
                        modifier = Modifier.padding(start = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    AddressCity(vmBasic)

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            if(AdminAccount.idOrg != null) {
                                vmBasic.onEvent(
                                    UiEventBasicInfoA.UpdateOrg(
                                        name = nameValue,
                                        phone = phoneValue,
                                        description = description ?: "",
                                        images = selectImages.toList(),
                                        context
                                    )
                                )
                            }
                            else{
                                vmBasic.onEvent(
                                    UiEventBasicInfoA.UpdateOrg(
                                        name = nameValue,
                                        phone = phoneValue,
                                        description = description,
                                        images = selectImages.toList(),
                                        context
                                    )
                                )
                            }
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(redActionColor),
                        shape = RoundedCornerShape(15)
                    ) {
                        Text(text = "Сохранить изменения", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(MaterialTheme.shapes.small)
                    .padding(top = 8.dp, start = 10.dp)
                    .size(45.dp),
                containerColor = backHeader,
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressCity(vmBasic: BasicInfoModelView) {
    val context = LocalContext.current
    val cities_ = vmBasic.cities.observeAsState()
    var cities = mutableListOf("")
    var selectedText = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    if (cities_.value != null) {
        cities = cities_.value!!.keys.toList().toMutableList()
        if(selectedText.value == "")
            selectedText.value = cities[0]
        val address = AddressSuggestModelView.addressTo?.observeAsState()
        if (address!!.value != null && address.value!!.lat != null) {
            val value = address.value!!
            vmBasic.onEvent(
                UiEventBasicInfoA.AddAddresss(
                    selectedText.value,
                    CityOrganization(
                        address = value.displayText,
                        points = Point(value.lat!!, value.lon!!)
                    )
                )
            )
            AddressSuggestModelView.removeAddressTo()
        }
    }

    Column() {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20))
                .background(orderCreateBackField)
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable { expanded = !expanded })
        {

            Text(
                text = selectedText.value.ifEmpty { "Добавьте город" },
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                color = grayList
            )

            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            DropdownMenu(expanded = expanded,
                modifier = Modifier.background(orderCreateBackField),
                onDismissRequest = {
                    expanded = false
                }) {
                addCity(vmBasic)

                cities.forEach { city ->

                    val isSelected = city == selectedText.value
                    val style = if (isSelected) {
                        MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = whiteColor
                        )
                    } else {
                        MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = grayList
                        )
                    }

                    DropdownMenuItem(
                        text = { Text(city, style = style) },
                        onClick = {
                            expanded = false
                            selectedText.value = city
                        }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
        text = "Адреса в городе", fontSize = 16.sp, color = grayList,
        modifier = Modifier.padding(start = 5.dp)
    )

    Spacer(modifier = Modifier.height(4.dp))

    for (value in cities_.value?.get(selectedText.value) ?: listOf()) {
        var openStateDelete by remember {
            mutableStateOf(false)
        }

        if (openStateDelete) {
            AlertDeleteAddress(
                confirmLogic = {
                    vmBasic.onEvent(UiEventBasicInfoA.RemoveAddresss(selectedText.value, value))
                    openStateDelete = false
                },
                dismissLogic = { openStateDelete = false }
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(adminAdressCity)
        ) {
            Row {
                Text(
                    text = value.address ?: "Адрес не найден ",
                    fontSize = 16.sp,
                    color = whiteColor,
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 15.dp, vertical = 12.dp)
                )

                Image(modifier = Modifier
                    .width(20.dp)
                    .height(24.dp)
                    .clickable {
                        openStateDelete = true
                    }
                    .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.trash_can_115312),
                    contentDescription = null)

                Spacer(modifier = Modifier.width(15.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }

    OutlinedCard(
        border = BorderStroke(2.dp, redActionColor),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(adminAdressAddCity),
        onClick = {
            AddressSuggestModelView.setPickCity(selectedText.value)
            val intent = Intent(context, MapSearchActivity::class.java)
            context.startActivity(intent)
        }
    ) {
        Row {
            Text(
                text = "Добавить адрес",
                fontSize = 16.sp,
                color = whiteColor,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .width(26.dp)
                    .height(26.dp)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Filled.Add,
                tint = whiteColor,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

@Composable
private fun AlertDeleteAddress(confirmLogic: () -> Unit, dismissLogic: () -> Unit) {
    AlertDialog(
        containerColor = orderCreateCard,
        onDismissRequest = {  },
        title = { Text("Вы уверены, что хотите удалить ресторан?!", fontSize = 20.sp, color = whiteColor) },
        text = { Text("По этому адресу пользователи не смогут найти ресторан на карте", fontSize = 12.sp, color = grayList) },
        confirmButton = {
            TextButton(colors = ButtonDefaults.buttonColors(redActionColor), onClick = {
                confirmLogic()
            }) {
                Text("Удалить".uppercase(), color = whiteColor)
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissLogic() }) {
                Text("Закрыть".uppercase(), color = grayList)
            }
        },
    )
}

@Composable
fun addCity(vmBasic: BasicInfoModelView) {
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledContainerColor = lightGrayColor,
        focusedContainerColor = lightGrayColor,
        unfocusedContainerColor = lightGrayColor,
    )

    var addState by remember { mutableStateOf(false) }
    if(!addState) {
        Row(modifier = Modifier
            .background(color = orderCreateCard)
            .clickable {
                addState = true
            }) {
            Spacer(modifier = Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp))
            Icon(
                imageVector = Icons.Filled.Add,
                tint = whiteColor,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "Добавить город",
                fontSize = 14.sp,
                color = whiteColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp, bottom = 10.dp, end = 15.dp, start = 6.dp)
            )
        }
    }
    else {
        var city by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { addState = false },
            title = { Text("Добавьте город в список", fontSize = 22.sp) },
            text = {
                TextField(
                    value = city,
                    onValueChange = {
                        city = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20)),
                    colors = colorET
                ) },
            confirmButton = {
                TextButton(onClick = { vmBasic.onEvent(UiEventBasicInfoA.AddCities(city)) }) {
                    Text("Добавить".uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { addState = false }) {
                    Text("Закрыть".uppercase())
                }
            },
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BasicInfof(){
}