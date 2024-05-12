package com.wayplaner.learn_room.admin.basic_info.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.basic_info.util.StatusBasicInfo
import com.wayplaner.learn_room.admin.basic_info.util.UiEventBasicInfoA
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.redLogoColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun BasicInfo(
    navController: NavController,
    vmBasic: BasicInfoModelView = hiltViewModel()) {
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledContainerColor = lightGrayColor,
        focusedContainerColor = lightGrayColor,
        unfocusedContainerColor = lightGrayColor,
    )

    vmBasic.onEvent(UiEventBasicInfoA.SearchOrg(1))

    val result = vmBasic.UiStatus.observeAsState()

    if (result.value == StatusBasicInfo.NoFoundInfo) {
        Text(
            text = "НИЧЕГО НЕ НАЙДЕНО", fontSize = 16.sp,
            modifier = Modifier.padding(start = 5.dp)
        )
    } else {

        Box(modifier = Modifier.fillMaxSize()) {

            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.burger_king),
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.height(30.dp))

                Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                    Text(
                        text = "Назввание ресторана", fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    TextField(
                        value = "Бургер кинг",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20)),
                        colors = colorET
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(redLogoColor),
                        shape = RoundedCornerShape(40)
                    ) {
                        Text(text = "Изменить фото")
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = { /*TODO*/ },
                    Modifier
                        .padding(horizontal = 25.dp)
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(redBlackColor),
                    shape = RoundedCornerShape(40)
                ) {
                    Text(text = "Сохранить изменения")
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(MaterialTheme.shapes.small)
                    .padding(top = 8.dp, start = 10.dp)
                    .size(45.dp),
                containerColor = whiteColor,
                onClick = { /*navController.navigateUp()*/ }) {
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


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BasicInfof(){
    //BasicInfo()
}