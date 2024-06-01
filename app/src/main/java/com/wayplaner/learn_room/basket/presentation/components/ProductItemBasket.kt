package com.wayplaner.learn_room.basket.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.basket.presentation.BasketModelView
import com.wayplaner.learn_room.order.data.model.ProductInBasket
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun ProductItemBasket(productInBasket: ProductInBasket, vmBasket: BasketModelView) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape (20.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
            Image(painter = painterResource(id = R.drawable.burger),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentDescription = null)

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(3f).padding(top = 8.dp)) {
                Text(text = productInBasket.product!!.name, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = productInBasket.product!!.price.toString(), color = grayColor_Text)
            }

            Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.End) {
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    IconButton(modifier = Modifier
                        .clip(CircleShape)
                        .size(26.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(lightGrayColor),
                        onClick = {
                            if(productInBasket.count == 1)
                                vmBasket.deleteProduct(productInBasket.product!!)
                            else
                                vmBasket.minusProduct(productInBasket.product!!)
                        }) {
                        Icon(
                            modifier = Modifier
                                .padding(3.dp),
                            painter = painterResource(id = R.drawable.minus_111123),
                            tint = whiteColor,
                            contentDescription = "add_i_product"
                        )
                    }
                    Text(text = productInBasket.count.toString(), fontSize = 16.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                    IconButton(modifier = Modifier
                        .clip(CircleShape)
                        .size(26.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(lightGrayColor),
                        onClick = {
                            if(productInBasket.count < 99)
                                vmBasket.plusProduct(productInBasket.product!!)
                        }) {
                        Icon(
                            modifier = Modifier
                                .padding(3.dp),
                            imageVector = Icons.Filled.Add,
                            tint = whiteColor,
                            contentDescription = "add_i_product"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = mathSumm(productInBasket), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

private fun mathSumm(product: ProductInBasket?): String{
    return (product!!.product!!.price!! * product.count!!).toString()
}
@Preview
@Composable
fun previewProductitem(){
}