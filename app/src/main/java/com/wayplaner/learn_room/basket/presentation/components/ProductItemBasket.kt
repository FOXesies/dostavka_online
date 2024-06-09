package com.wayplaner.learn_room.basket.presentation.components

import android.graphics.BitmapFactory
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.basket.presentation.BasketModelView
import com.wayplaner.learn_room.order.data.model.IdsProductInBasket
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun ProductItemBasket(productInBasket: IdsProductInBasket, vmBasket: BasketModelView, product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        colors = CardDefaults.cardColors(backHome),
        shape = RoundedCornerShape (20.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
            if (product.images == null || product.images!!.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.no_fof),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentDescription = null
                )
            }
            else{
                val images = remember { mutableListOf<ImageBitmap>() }
                product.images?.forEach {
                    images.add(BitmapFactory.decodeByteArray(it!!.value, 0, it.value!!.size).asImageBitmap())
                }
                Image(
                    bitmap = images[0],
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(3f).padding(top = 8.dp)) {
                Text(text = product.name, fontSize = 16.sp, color = whiteColor)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text =product.price.toString(), color = whiteColor)
            }

            Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.End) {
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    IconButton(modifier = Modifier
                        .clip(CircleShape)
                        .size(26.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(redActionColor),
                        onClick = {
                            if(productInBasket.count == 1)
                                vmBasket.deleteProduct(product!!)
                            else
                                vmBasket.minusProduct(product!!)
                        }) {
                        Icon(
                            modifier = Modifier
                                .padding(3.dp),
                            painter = painterResource(id = R.drawable.minus_111123),
                            tint = whiteColor,
                            contentDescription = "add_i_product"
                        )
                    }
                    Text(text = productInBasket.count.toString(), fontSize = 16.sp, color = whiteColor, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                    IconButton(modifier = Modifier
                        .clip(CircleShape)
                        .size(26.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(redActionColor),
                        onClick = {
                            if(productInBasket.count < 99)
                                vmBasket.plusProduct(product!!)
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
                Text(text = mathSumm(product!!, productInBasket.count), color = whiteColor, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

private fun mathSumm(product: Product, count: Int): String{
    return (product.price!! * count).toString()
}
@Preview
@Composable
fun previewProductitem(){
}