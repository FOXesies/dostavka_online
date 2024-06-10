package com.wayplaner.learn_room.createorder.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.whiteColor
import org.example.order.model.StatusPayment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayViewCardOrder(logicUpdate: (StatusPayment) -> Unit) {
    val status = remember { mutableStateOf(StatusPayment.CARD.getName(StatusPayment.CARD)) }
    val radioOptions = listOf("Картой", "Наличными")
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Способ оплаты", fontSize = 18.sp, color = grayList)
            logicUpdate(StatusPayment.CARD)
            Spacer(modifier = Modifier.height(20.dp))

            val context = LocalContext.current

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp),
                onClick = { /*TODO*/ }) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {

                        RadioButton(
                            // inside this method we are
                            // adding selected with a option.
                            selected = (radioOptions[0] == selectedOption.value),
                            colors = RadioButtonDefaults.colors(selectedColor = whiteColor, unselectedColor = grayColor),
                            modifier = Modifier.padding(all = Dp(value = 8F)),
                            onClick = {
                                logicUpdate(StatusPayment.CARD)
                                selectedOption.value = radioOptions[0]
                            }
                        )
                        // below line is use to add
                        // text to our radio buttons.
                        Text(
                            text = radioOptions[0],
                            color = if(radioOptions[0] == selectedOption.value) whiteColor else grayColor,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {

                        RadioButton(
                            selected = (radioOptions[1] == selectedOption.value),
                            colors = RadioButtonDefaults.colors(selectedColor = whiteColor, unselectedColor = grayColor),
                            modifier = Modifier.padding(all = Dp(value = 8F)),
                            onClick = {
                                selectedOption.value = radioOptions[1]
                                logicUpdate(StatusPayment.CASH)
                            }
                        )
                        // below line is use to add
                        // text to our radio buttons.
                        Text(
                            text = radioOptions[1],
                            color = if(radioOptions[1] == selectedOption.value) whiteColor else grayColor,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(25.dp))

        }
    }
}
