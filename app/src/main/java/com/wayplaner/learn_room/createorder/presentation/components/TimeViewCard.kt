package com.wayplaner.learn_room.createorder.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.createorder.presentation.CreateOrderModelView
import com.wayplaner.learn_room.createorder.util.OrderFormState
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.whiteColor
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeViewCard(
    vmCreateOrder: CreateOrderModelView
) {
    var textColorValue = TextFieldDefaults.textFieldColors(
        focusedTextColor = whiteColor,
        unfocusedTextColor = grayList,
        containerColor = Color.Transparent,
        cursorColor = whiteColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedLabelColor = whiteColor,
        unfocusedLabelColor = grayList,
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Доставить ко времени", fontSize = 18.sp, color = grayList)

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    val currentDate = LocalDate.now()
                    val currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
                    val roundedCurrentTime = currentTime.plusMinutes((90 - currentTime.minute % 5).toLong())
                    val timeIntervals = generateTimeIntervals(currentDate, roundedCurrentTime, 14)

                    var selectedTime by remember { mutableStateOf(timeIntervals[0]) }

                    LaunchedEffect(Unit){
                        vmCreateOrder.onValidateEvent(OrderFormState.ToTimeChaged(timeIntervals[0]))
                    }

                    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

                    Column {

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                value = selectedTime.format(timeFormatter)!!,
                                onValueChange = {
                                    expanded = true
                                },
                                readOnly = true,
                                shape = RoundedCornerShape(16.dp),
                                textStyle = TextStyle(fontSize = 16.sp),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.AccessTime,
                                        contentDescription = null,
                                        tint = grayList
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = grayList
                                    )
                                },
                                colors =textColorValue,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                placeholder = {
                                    Text(text = "Выберите город", fontSize = 16.sp, color = grayList)
                                }
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                timeIntervals.forEach { time ->
                                    DropdownMenuItem(
                                        text = { Text(text = time.format(timeFormatter)) },
                                        onClick = {
                                            selectedTime = time
                                            expanded = false
                                            vmCreateOrder.onValidateEvent(OrderFormState.ToTimeChaged(time))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateTimeIntervals(currentDate: LocalDate, startTime: LocalTime, count: Int): List<LocalDateTime> {
    val intervals = mutableListOf<LocalDateTime>()
    var currentTime = startTime
    for (i in 1..count) {
        intervals.add(LocalDateTime.of(currentDate, currentTime))
        currentTime = currentTime.plusMinutes(15)
    }
    return intervals
}