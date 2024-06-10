package com.wayplaner.learn_room.home.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import com.gowtham.ratingbar.RatingBar
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.lightGrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Organization(
    navController: NavController,
    organization: OrganizationDTO,
    route: String
) {
    OutlinedCard(
        border = BorderStroke((0.8).dp, Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(backOrgHome),
        onClick = {
            navController.navigate(route)
        }
    ) {
        val lazyListState = rememberLazyListState()
        val loader = ImageLoader(LocalContext.current)
        Column(
        ) {
                //if (!organization.images.isNullOrEmpty())
            val pickImage = organization.idImages.value
            val bitmap = BitmapFactory.decodeByteArray(pickImage, 0, pickImage!!.size)
            val imageBitmap = bitmap.asImageBitmap()
            Image(bitmap = imageBitmap, contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.Transparent),
                shape = MaterialTheme.shapes.small,
            ) {
                Row(
                    modifier = Modifier.padding(top = 6.dp, start = 14.dp)
                ) {
                    Column(Modifier.weight(1f)) {

                        Text(
                            text = organization.name,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp),
                            color = lightGrayColor,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(8.dp))

                    }

                    Spacer(Modifier.width(8.dp))

                    Column(modifier = Modifier.padding(top = 4.dp, end = 14.dp, bottom = 6.dp)) {

                        if (organization.rating != -1.0) {
                            RatingBar(
                                value = organization.rating!!.toFloat(),
                                size = 18.dp,
                                spaceBetween = 0.5.dp,
                                painterEmpty = painterResource(id = R.drawable.empty_rating),
                                painterFilled = painterResource(id = R.drawable.fill_rating),
                                onValueChange = {},
                                onRatingChanged = {
                                    Log.d("TAG", "onRatingChanged: $it")
                                })

                            Text(
                                modifier = Modifier.align(Alignment.End),
                                text = organization.ratingCount.toString() + " " + parseCountToString(
                                    organization.ratingCount!!
                                ),
                                fontSize = 14.sp,
                                color = grayList,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }


                        Spacer(Modifier.height(8.dp))

                        /*Text(
                            modifier = Modifier.align(Alignment.End),
                            text = "10:00-22:00",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.labelMedium
                        )*/
                    }
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

private fun byteArrayToBitmap(data: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(data, 0, data.size)
}
