package com.wayplaner.learn_room.admin.menu.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.admin.menu.presentation.components.AddProductView
import com.wayplaner.learn_room.admin.menu.presentation.components.CategoryAdminView
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun MenuAddScreen(
    navController: NavController,
    modelView: MenuModelView = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()){
        MenuExist(modelView)

        Text(text = "Добавление блюда", fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 18.dp, start = 80.dp, end = 20.dp)
                .fillMaxWidth())

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .clip(MaterialTheme.shapes.small)
                .padding(top = 8.dp, start = 10.dp, end = 2.dp, bottom = 2.dp)
                .size(45.dp),
            containerColor = whiteColor,
            onClick = { navController.navigateUp() }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                tint = redActionColor,
                modifier = Modifier.size(32.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun MenuUpdateScreen(modelView: MenuModelView){
    Box(modifier = Modifier.fillMaxSize()){
        MenuExist(modelView)

        Text(text = "Изменение блюда", fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 18.dp, start = 80.dp, end = 20.dp)
                .fillMaxWidth())

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .clip(MaterialTheme.shapes.small)
                .padding(top = 8.dp, start = 10.dp, end = 2.dp, bottom = 2.dp)
                .size(45.dp),
            containerColor = whiteColor,
            onClick = { /*navController.navigateUp()*/ }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                tint = redActionColor,
                modifier = Modifier.size(32.dp),
                contentDescription = null
            )
        }
    }
}
@Composable
private fun MenuExist(modelView: MenuModelView){
    Column(
        modifier = Modifier.padding(top = 70.dp)){
        Text(text = "Категория блюда", fontSize = 16.sp, modifier = Modifier.padding(start = 20.dp, bottom = 2.dp))
        CategoryAdminView(modelView)

        Spacer(modifier = Modifier.height(15.dp))

        AddProductView()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Menu_f(){

}