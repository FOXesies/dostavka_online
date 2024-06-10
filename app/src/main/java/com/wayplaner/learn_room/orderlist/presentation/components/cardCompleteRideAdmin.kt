package com.wayplaner.learn_room.orderlist.presentation.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.errorStatus
import com.wayplaner.learn_room.ui.theme.errorStatusBack
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.starColor
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCompleteOrder(
    nameOrg: String?,
    orderId: Long,
    timeFrom: String?,
    summ: Double?,
    isDelivery: Boolean,
    feedback: Int? = null,
    logicOpen: () -> Unit,
    logicCreateFeedback: (Int, String?) -> Unit?)
{
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(backOrgHome)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {

            var rating by remember { mutableIntStateOf(feedback ?: 0) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                cardForStatusDelivery(isDelivery)

                if (rating != 0) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Ваша оценка: ",
                        fontSize = 14.sp, modifier = Modifier
                            .padding(vertical = 0.dp),
                        textAlign = TextAlign.Center, color = summRedColor
                    )

                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = summRedColor,
                        modifier = Modifier
                            .size(22.dp)
                    )
                    Text(
                        text = rating.toString(),
                        fontSize = 14.sp, modifier = Modifier
                            .padding(vertical = 0.dp),
                        textAlign = TextAlign.Center, color = summRedColor
                    )

                }
            }
            Card(
                colors = CardDefaults.cardColors(errorStatusBack)
            ) {
                Text(
                    text = "Заказ доставлен",
                    color = errorStatus,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            Text(
                text = "Ресторан: $nameOrg", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                color = whiteColor
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ID заказа", color = grayList)
                    Text(text = orderId.toString(), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Дата", color = grayList)
                    Text(text = getLocalDateTime(timeFrom!!), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = grayList)
                    Text(text = "$summ руб", color = whiteColor)
                }
            }

            Column(modifier = Modifier.padding(top = 20.dp)) {

                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = { logicOpen() }) {
                    Text(
                        text = "Посмотреть заказ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                if (rating == 0) {
                    var stateFeedbacks by remember { mutableStateOf(false) }
                    if (stateFeedbacks) {
                        RatingDialog(0,
                            nameOrg!!,
                            onRatingSelected = { rating_, comment_ ->
                                rating = rating_
                                logicCreateFeedback(rating_, comment_)
                                stateFeedbacks = false
                            },
                            onDismiss = {
                                stateFeedbacks = false
                            })
                    }

                    Button(shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(42.dp),
                        colors = ButtonDefaults.buttonColors(redActionColor),
                        onClick = { stateFeedbacks = true }) {
                        Text(
                            text = "Оставьте отзыв ☺\uFE0F", modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDialog(
    rating: Int,
    nameOrg: String,
    onRatingSelected: (Int, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedRating by remember { mutableStateOf(rating) }

    val comment = remember {
        mutableStateOf("")
    }

    AlertDialog(
        containerColor = orderCreateCard,
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Ваш отзыв важен для нас! \"$nameOrg\"", color = whiteColor)
        },
        text = {
            Column {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(8.dp)
                ) {
                    for (i in 1..5) {
                        val starColor = if (i <= selectedRating) summRedColor else starColor
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier
                                .size(38.dp)
                                .padding(4.dp)
                                .clickable(
                                    indication = null, // Remove the ripple effect
                                    interactionSource = remember { MutableInteractionSource() } // Prevents any state change or effect
                                ) {
                                    selectedRating = i
                                }
                        )
                    }
                }

                Text(
                    text = "Комментарий", color = whiteColor, fontSize = 14.sp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, top = 15.dp),
                )
                Card(shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(orderCreateBackField)) {
                    TextField(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                        value = comment.value,
                        onValueChange = { comment.value = it },
                        colors = TextFieldDefaults
                            .textFieldColors(
                                focusedTextColor = whiteColor,
                                unfocusedTextColor = grayList,
                                containerColor = Color.Transparent,
                                cursorColor = whiteColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = whiteColor,
                                unfocusedLabelColor = grayList
                            )
                    )
                }
            }
        },
        confirmButton = {
            val color = remember { redActionColor }
            val context = LocalContext.current
            TextButton(colors = if (selectedRating != 0) ButtonDefaults.buttonColors(color) else ButtonDefaults.buttonColors(color.copy(alpha = 0.5f)),
                onClick = {
                    if (selectedRating != 0)
                        onRatingSelected(selectedRating, comment.value)
                    else
                        Toast.makeText(context, "Выберите значение оценки", Toast.LENGTH_LONG).show()
                }
            ) {
                Text("Отправить", color = whiteColor, fontSize = 16.sp, modifier = Modifier.padding(4.dp))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Закрыть", color = whiteColor, fontSize = 16.sp, modifier = Modifier.padding(4.dp))
            }
        }
    )
}

