package com.wayplaner.learn_room.organization.presentation

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.categoryColor
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.titleProductColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrganizationCardOrg(
    navigateUp: () -> Unit,
    navigateToSelectedProduct: (Long) -> Unit,
    organizationViewModel: OrganizationModelView = hiltViewModel()){

    val organization = organizationViewModel.getOrganization().observeAsState()

    if (organization.value != null) {
        val organization_const = organization.value!!
        Column {
            Box() {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.burger_king),
                    contentDescription = ""
                )

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 200.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(whiteColor)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    ) {

                        Row(modifier = Modifier.padding(end = 10.dp)) {
                            Text(
                                fontSize = 22.sp,
                                color = redBlackColor,
                                text = "${organization_const.name}",
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.ag_cooper_cyr,
                                        FontWeight.Medium
                                    )
                                ),
                                modifier = Modifier
                                    .weight(4f)
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(
                                        Icons.Filled.StarBorder,
                                        tint = redBlackColor,
                                        contentDescription = "star_organization"
                                    )

                                    Text(
                                        text = "${organization_const.rating}",
                                        modifier = Modifier.padding(top = 1.dp, start = 2.dp),
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.montserrat_alternates,
                                                FontWeight.SemiBold
                                            )
                                        ),
                                        fontSize = 14.sp
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        text = "${organization_const.ratingCount} ${
                                            parseCountToString(
                                                organization_const.ratingCount!!
                                            )
                                        }",
                                        color = grayColor,
                                        modifier = Modifier.padding(top = 1.dp, start = 2.dp),
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.bajkal,
                                                FontWeight.SemiBold
                                            )
                                        ),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Text(
                            fontSize = 14.sp,
                            color = grayColor_Text,
                            text = /*"Рестораны сильно различаются по внешнему виду и предложениям, " +
                                        "включая широкий выбор кухонь и моделей обслуживания, начиная от " +
                                        "недорогих ресторанов быстрого питания и кафетериев, до семейных " +
                                        "ресторанов средней ценовой категории и дорогихроскошных заведений.",*/
                            "${organization_const.descriptions}",
                            fontFamily = FontFamily(Font(R.font.bajkal, FontWeight.Medium)),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Text(
                            text = "Адрес: ${organization_const.locationsAll.keys.first()}, ${organization_const.locationsAll.values.first()[0].address}",
                            fontFamily = FontFamily(Font(R.font.ag_cooper_cyr, FontWeight.Medium)),
                            modifier = Modifier
                                .padding(top = 30.dp)
                        )
                    }
                    Column {
                        MainContent(organization_const, navigateToSelectedProduct)
                        //RowCategory(listOf("все", "десерты", "супы", "роллы", "пицца", "алкоголь", "шашлыки"))
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(MaterialTheme.shapes.small)
                        .padding(top = 8.dp, start = 10.dp)
                        .size(45.dp),
                    containerColor = whiteColor,
                    onClick = { navigateUp() }) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        tint = redActionColor,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Добавить"
                    )
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(MaterialTheme.shapes.small)
                        .padding(top = 8.dp, end = 10.dp)
                        .size(45.dp),
                    containerColor = redActionColor,
                    onClick = { /*navigateUp()*/ }) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        tint = whiteColor,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Добавить"
                    )
                }
            }
        }
    }
}

fun parseCountToString(count: Int): String{
    if(count == 1)
        return "отзыв"
    else if(count in 11..14)
        return "отзывов"
    else if(count % 10 in 2..4)
        return "отзыва"
    else
        return "отзывов"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(oraganization_const: OrganizationIdDTO, navigateToSelectedProduct: (Long) -> Unit) {
    val categories = oraganization_const.category.toMutableList()
    categories.add(0, "Все")

    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf("Все") }
    val pagerState = rememberPagerState {
        categories.size
    }

    Column() {
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Transparent,
            contentColor = Color(0xFFFEFEFA),
            indicator = {
                Spacer(
                    Modifier
                        .tabIndicatorOffset(it[selectedIndex])
                        .height(2.5.dp)
                        .background(redActionColor)
                )
            }
        ) {
            categories.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        selectedCategory = tab
                    },
                ) {
                    Text(
                        color = if (selectedIndex == index) redActionColor else categoryColor,
                        text = tab,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { index ->
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                items(
                    if(selectedCategory == "Все")
                        oraganization_const.products.flatMap { it.value }.sortedBy { it.name }
                    else
                        oraganization_const.products[selectedCategory]!!
                ) {
                    ProductCard(it, navigateToSelectedProduct)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, navigateToSelectedProduct: (Long) -> Unit ) {
    Card(modifier = Modifier
        .wrapContentHeight(Alignment.CenterVertically)
        .clickable { navigateToSelectedProduct(product.idProduct!!) },
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(whiteColor)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)) {
            Image(
                modifier = Modifier
                    .size(92.dp)
                    .align(Alignment.CenterVertically)
                    .clip(MaterialTheme.shapes.small)
                ,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.burger),
                contentDescription = "card_product_for_category_organization"
            )
            Column(modifier = Modifier.padding(start = 14.dp, end = 5.dp)) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.sp,
                    color = titleProductColor
                )

                product.description?.let {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = it,//"это горячее блюдо, обычно состоящее из котлеты из измельчённого мяса, как правило, говядины, помещённой внутрь нарезанной булочки. Гамбургеры часто подают с сыром, салатом, помидорами, луком, маринованными огурцами, беконом или чили; соусами, такими как кетчуп, горчица, майонез, релиш; и часто их кладут на булочки с кунжутом.",
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = categoryColor,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(modifier = Modifier.padding(top = 10.dp)){
                    Text(modifier = Modifier
                        .weight(2f),
                        text = "${product.price} руб.",
                        color = redBlackColor,
                        fontSize = 16.sp
                    )

                    IconButton(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape),
                        colors = IconButtonDefaults.filledIconButtonColors(redBlackColor),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add_in_organization",
                            tint = whiteColor // Устанавливаем размер иконки
                        )
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun TestOrganization(){
    
/*    CategoryListpick(OrganizationIdDTO(1,
        "Бургер кинг",
        "улица Административная, дом 30г",
        "+89308391610",
        "г. Москва",
        "Лучший ресторан, который был построен за сегодня",
        listOf("Пицца", "Паста"*//*, "Суп", "Напитки"*//*),
        mapOf("Пицца" to listOf(
            Product(1, "Бургер пицца", 500.0, 500.0f, "Описание Пицца 1", null),
            Product(2, "Цезарь пицца", 380.0, 470.0f, "Описание Пицца 2", null)
        ),
            "Паста" to listOf(
                Product(3, "Карбонара", 700.0, 400.0f, "Описание Паста 1", null),
                Product(4, "Фетучинни", 900.0, 420.0f, "Описание Паста 2", null)
            )),
        4.5,
        5,
        null
    ))*/
    //OrganizationCardOrg()
}