package com.wayplaner.learn_room.organization.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.categoryColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.titleProductColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(modifier = Modifier
        .wrapContentHeight(Alignment.CenterVertically)
        .clickable { navController.navigate("${MainRoute.Product.name}/${product.idProduct}") },
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
                    Text(modifier = Modifier.padding(top = 10.dp),
                        text = "${product.price} руб.",
                        color = redBlackColor,
                        fontSize = 16.sp
                    )
            }
        }
    }
}