package com.wayplaner.learn_room.admin.menu.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.admin.menu.presentation.MenuModelView
import com.wayplaner.learn_room.admin.menu.util.UiEventMenuAdd
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun CategoryAdminView(modelView: MenuModelView, category: String) {
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledContainerColor = lightGrayColor,
        focusedContainerColor = lightGrayColor,
        unfocusedContainerColor = lightGrayColor,
    )

    var selectedCategory by remember { mutableStateOf("") }
    selectedCategory = category
    modelView.onEvent(UiEventMenuAdd.ChangeCategoryProduct(category))
    var addState by remember { mutableStateOf(false) }

    val categories = modelView.categories.value

    if (categories != null) {
        LazyRow(Modifier.fillMaxWidth()) {
            items(categories) {
                Button(modifier = Modifier
                    .padding(horizontal = 5.dp),
                    colors = if (it == selectedCategory) ButtonDefaults.buttonColors(redActionColor) else ButtonDefaults.buttonColors(
                        grayColor
                    ),
                    onClick = {
                        selectedCategory = it
                        modelView.onEvent(UiEventMenuAdd.ChangeCategoryProduct(it))
                    }) {
                    Text(
                        text = it,
                        color = whiteColor,
                        fontSize = 14.sp
                    )
                }
            }
            item {
                Button(modifier = Modifier
                    .padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(grayColor),
                    contentPadding = PaddingValues(start = 10.dp, end = 18.dp),
                    onClick = { addState = true }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        Modifier.padding(start = 0.dp)
                    )
                    Text(
                        text = "Добавить",
                        color = whiteColor,
                        fontSize = 14.sp
                    )
                }
            }
        }
    } else {
        Button(modifier = Modifier
            .padding(horizontal = 5.dp)
            .padding(top = 80.dp),
            colors = ButtonDefaults.buttonColors(grayColor),
            contentPadding = PaddingValues(start = 10.dp, end = 18.dp),
            onClick = { addState = true }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                Modifier.padding(start = 0.dp)
            )
            Text(
                text = "Добавить",
                color = whiteColor,
                fontSize = 14.sp
            )
        }
    }

    if (addState) {
        var category by remember { mutableStateOf("") }
        AlertDialog(
            containerColor = backHome,
            onDismissRequest = { addState = false },
            title = { Text("Добавьте категорию товара", fontSize = 22.sp, color = whiteColor) },
            text = {
                TextField(
                    value = category,
                    onValueChange = {
                        category = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20)),
                    colors = colorET
                )
            },
            confirmButton = {
                TextButton(colors = ButtonDefaults.buttonColors(redActionColor), onClick = {
                    modelView.onEvent(UiEventMenuAdd.AddCategoryInList(category))
                    addState = false
                }) {
                    Text("Добавить".uppercase(), color = whiteColor, modifier = Modifier.padding(4.dp))
                }
            },
            dismissButton = {
                TextButton(onClick = { addState = false }) {
                    Text("Закрыть".uppercase(), color = grayColor)
                }
            },
        )
    }
}