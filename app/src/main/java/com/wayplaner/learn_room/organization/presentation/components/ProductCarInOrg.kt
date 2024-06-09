package com.wayplaner.learn_room.organization.presentation.components

import android.graphics.BitmapFactory
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun ProductCard(product: ResponseProductOrg, orgId: Long, navController: NavController, clickable: Boolean = true) {
    Card(modifier = Modifier
        .wrapContentHeight(Alignment.CenterVertically)
        .clickable { if(clickable) navController.navigate("${MainRoute.Product.name}/${orgId}/${product.id}") },
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(backOrgHome)) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)) {

            if(product.image != null) {
                val pickImage = product.image!!.value
                val bitmap =
                    remember { BitmapFactory.decodeByteArray(pickImage, 0, pickImage!!.size) }
                val imageBitmap = remember { bitmap.asImageBitmap() }

                Image(
                    modifier = Modifier
                        .size(92.dp)
                        .align(Alignment.CenterVertically)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                    bitmap = imageBitmap,
                    contentDescription = "card_product_for_category_organization"
                )
            }
            else {
                Image(
                    modifier = Modifier
                        .size(92.dp)
                        .align(Alignment.CenterVertically)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.no_fof),
                    contentDescription = "card_product_for_category_organization")
            }
            Column(modifier = Modifier.padding(start = 14.dp, end = 5.dp)) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.sp,
                    color = whiteColor
                )

                product.description?.let {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = it,//"это горячее блюдо, обычно состоящее из котлеты из измельчённого мяса, как правило, говядины, помещённой внутрь нарезанной булочки. Гамбургеры часто подают с сыром, салатом, помидорами, луком, маринованными огурцами, беконом или чили; соусами, такими как кетчуп, горчица, майонез, релиш; и часто их кладут на булочки с кунжутом.",
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = grayList,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                    Text(modifier = Modifier.padding(top = 10.dp),
                        text = "${product.price} руб.",
                        color = summRedColor,
                        fontSize = 16.sp
                    )
            }
        }
    }
}