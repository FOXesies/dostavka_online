package com.wayplaner.learn_room.admin.basic_info.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.admin.basic_info.util.StatusBasicInfo
import com.wayplaner.learn_room.admin.basic_info.util.UiEventBasicInfoA
import com.wayplaner.learn_room.admin.util.toBitmapImage
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.redLogoColor
import com.wayplaner.learn_room.ui.theme.testText
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun BasicInfo(
    navController: NavController,
    vmBasic: BasicInfoModelView = hiltViewModel()) {

    val context = LocalContext.current
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledContainerColor = lightGrayColor,
        focusedContainerColor = lightGrayColor,
        unfocusedContainerColor = lightGrayColor,
    )

    //vmBasic.onEvent(UiEventBasicInfoA.SearchOrg(1))
    var imageUpdate by remember { mutableStateOf(false) }
    var infoUpdate by remember { mutableStateOf(false) }
    val result = vmBasic.UiStatus.observeAsState()

    if (result.value != null) {
        if (result.value == StatusBasicInfo.NoFoundInfo) {
            Text(
                text = "НИЧЕГО НЕ НАЙДЕНО", fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        } else {
            val resultFound = (result.value as StatusBasicInfo.FoundInfo)
            var nameValue by remember { mutableStateOf(resultFound.organizationResponse.name) }
            val phoneValue by remember { mutableStateOf(resultFound.organizationResponse.phoneForUser) }
            var description by remember { mutableStateOf(resultFound.organizationResponse.descriptions) }
            var selectImages by remember { mutableStateOf(resultFound.image) }

            val galleryLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                    val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
                    val byteArray = inputStream?.readBytes()
                    if(byteArray != null) {
                        selectImages = byteArray
                        imageUpdate = true
                    }
                }

            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop,
                        bitmap = selectImages.toBitmapImage(),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                        Text(
                            text = "Название ресторана", fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        TextField(
                            value = nameValue,
                            onValueChange = {
                                nameValue = it
                                infoUpdate = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20)),
                            colors = colorET
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Номер телефона", fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        TextField(
                            value = phoneValue,
                            onValueChange = {
                                nameValue = it
                                infoUpdate = true
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20)),
                            colors = colorET
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Описание ресторана", fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        TextField(
                            value = description ?: "",
                            onValueChange = {
                                description = it
                                infoUpdate = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20)),
                            colors = colorET
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Адреса ресторана", fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        AddressCity(vmBasic)

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(redLogoColor),
                            shape = RoundedCornerShape(40)
                        ) {
                            Text(text = "Изменить фото")
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        Button(
                            onClick = {
                                if (imageUpdate)
                                    vmBasic.onEvent(
                                        UiEventBasicInfoA.UpdateImage(
                                            1,
                                            context,
                                            selectImages
                                        )
                                    )
                                if (!nameValue.isNullOrEmpty() && infoUpdate)
                                    vmBasic.onEvent(
                                        UiEventBasicInfoA.UpdateOrg(
                                            1,
                                            nameValue,
                                            phoneValue
                                        )
                                    )
                            },
                            Modifier
                                .fillMaxWidth()
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(redBlackColor),
                            shape = RoundedCornerShape(40)
                        ) {
                            Text(text = "Сохранить изменения")
                        }
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(MaterialTheme.shapes.small)
                        .padding(top = 8.dp, start = 10.dp)
                        .size(45.dp),
                    containerColor = whiteColor,
                    onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        tint = redActionColor,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Добавить"
                    )
                }
            }
        }
    }
}

@Composable
fun AddressCity(vmBasic: BasicInfoModelView) {
    val cities_ = vmBasic.cities.observeAsState()
    if (cities_.value != null){
        val cities = cities_.value!!.keys.toList()

        Column {
            val context = LocalContext.current
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf(cities[0]) }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20))
                    .background(lightGrayColor)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable { expanded = !expanded })
            {

                Text(
                    text = selectedText,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                DropdownMenu(expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }) {
                    addCity(vmBasic)

                    cities.forEach { city ->

                        val isSelected = city == selectedText
                        val style = if (isSelected) {
                            MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        } else {
                            MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        DropdownMenuItem(
                            text = { Text(city, style = style) },
                            onClick = {
                                expanded = false
                                selectedText = city
                            }
                        )
                    }
                }
            }
        }
    }
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
            .background(color = testText)
            .clickable {
                addState = true
            }) {
            Spacer(modifier = Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp))
            Icon(
                imageVector = Icons.Filled.Add,
                tint = whiteColor,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "Добавить город",
                fontSize = 16.sp,
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
        /*Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .clickable { addState = !addState }) {
            addCityField()
        }*/
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BasicInfof(){
}