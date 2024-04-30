package com.wayplaner.learn_room.home.presentation.components
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.home.presentation.MainModelView
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(drawerState: DrawerState?, homeViewModel: MainModelView,
               /*navigateToBasket: (Long) -> Unit,*/) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        modifier = Modifier.height(55.dp),
        title = {
            val cities = homeViewModel.getCity().value
            DropDownCity(cities!!, homeViewModel)
                },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = whiteColor,
            titleContentColor = redActionColor,
        ),
        navigationIcon = {
            if (drawerState != null) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(painterResource(id = R.drawable.hamburger_button_menu_icon_155296), contentDescription = "")
                }
            }
        },
        actions = {
            IconButton(modifier = Modifier.padding(top = 4.dp), onClick = { /*navigateToBasket(1)*/ }) {
                Icon(
                    Icons.Filled.ShoppingBasket,
                    contentDescription = "Basket",
                    tint = redActionColor,
                    modifier = Modifier.size(27.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownCity(state: List<String>, homeViewModel: MainModelView){
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(state[0]) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Back arrow here
        Row(Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { // Anchor view
            expanded = !expanded
        }) { // A
            // nchor view
            Text(text = selectedText, fontSize = 14.sp) // City name label
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            DropdownMenu(expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }) {
                state.forEach { city ->

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
                        text =  { Text(city, style = style) },
                        onClick = {
                            expanded = false
                            selectedText = city
                            homeViewModel.getOrganizationsByCity(selectedText)
                        }
                    )
                }
                //Text("Popup content \nhere", Modifier.padding(24.dp))
            }
        }
    }

    /*Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent),
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = !expanded
            }
        ) {
            selectedText?.let {
                TextField(
                    value = it,
                    onValueChange = {},
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 12.sp)),
                    leadingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                state.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            if (item != null) {
                                Text(text = item)
                            }
                        },
                        onClick = {
                            selectedText = item
                            expanded = false
                            //homeViewModel.setCityFilter(selectedText!!)
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }*/
}
