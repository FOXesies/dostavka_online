package com.wayplaner.learn_room.createorder.presentation.components

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CommentBank
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.wayplaner.learn_room.createorder.presentation.CreateOrderModelView
import com.wayplaner.learn_room.createorder.util.AddressPick
import com.wayplaner.learn_room.createorder.util.OrderFormState
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.textFieldHint
import com.wayplaner.learn_room.ui.theme.whiteColor

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfDeliveryPick(vmCreateOrder: CreateOrderModelView) {
    var textColorValue = TextFieldDefaults.textFieldColors(
        focusedTextColor = whiteColor,
        unfocusedTextColor = grayList,
        containerColor = Color.Transparent,
        cursorColor = whiteColor,
        focusedIndicatorColor = whiteColor,
        unfocusedIndicatorColor = grayList,
        focusedLabelColor = whiteColor,
        unfocusedLabelColor = grayList,
    )

    Column(
        modifier = Modifier
            .background(color = orderCreateCard)
            .padding(horizontal = 2.dp)
            .background(backOrgHome)
            .verticalScroll(rememberScrollState())
    ) {

        val addresses = vmCreateOrder.address.observeAsState()
        var expanded by remember { mutableStateOf(false) }

        if (addresses.value != null) {

            val valueAddress = addresses.value!!
            var selectedAddress by remember { mutableStateOf(valueAddress[0]) }

            PhoneViewCard(vmCreateOrder)

            Spacer(modifier = Modifier.height(10.dp))

            TimeViewCard(vmCreateOrder)

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(orderCreateCard)
            ) {

                Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Адрес ресторана", fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(orderCreateBackField),
                        shape = RoundedCornerShape(14.dp),
                        onClick = { /*TODO*/ }) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            LaunchedEffect(Unit) {
                                vmCreateOrder.onValidateEvent(
                                    OrderFormState.IdAddressChanged(
                                        valueAddress[0].idLocation!!
                                    )
                                )
                            }

                            Column {

                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }
                                ) {
                                    TextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor(),
                                        value = selectedAddress.address!!,
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
                                                contentDescription = null
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = whiteColor,
                                            unfocusedTextColor = grayList,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                        placeholder = {
                                            Text(text = "Выберите город", fontSize = 16.sp)
                                        }
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        valueAddress.forEach { address ->
                                            DropdownMenuItem(
                                                text = { Text(text = address.address!!) },
                                                onClick = {
                                                    selectedAddress = address
                                                    AddressPick.address.postValue(address)
                                                    expanded = false
                                                    vmCreateOrder.onValidateEvent(
                                                        OrderFormState.IdAddressChanged(
                                                            address.idLocation!!
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    AddressPick.lifecycle = LocalLifecycleOwner.current

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        AndroidView(factory = {

                            val mapFragment = MapFragment()
                            val inflater = LayoutInflater.from(it)
                            val container: ViewGroup? = null

                            mapFragment.onCreateView(inflater, container, Bundle())

                        })
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.padding(bottom = 30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CommentBank,
                            modifier = Modifier.size(21.dp),
                            tint = textFieldHint,
                            contentDescription = null
                        )

                        TextField(
                            colors = textColorValue,
                            value = vmCreateOrder.comment.value,
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = { vmCreateOrder.comment.value = it },
                            label = { Text(text = "Комментарий") },
                        )
                    }
                }
            }

        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    PayViewCardOrder()

}

@Composable
@Preview
fun previewcreateor(){
    //SelfDeliveryPick(vmCreateOrder)
}