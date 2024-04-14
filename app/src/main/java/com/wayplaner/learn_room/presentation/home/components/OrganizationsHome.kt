package com.wayplaner.learn_room.presentation.home.components

import android.graphics.drawable.BitmapDrawable
import android.util.Log
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gowtham.ratingbar.RatingBar
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.domain.model.OrganizationDTO
import com.wayplaner.learn_room.ui.theme.whiteColor
import com.wayplaner.learn_room.utils.ImageUtils.Companion.convertInputStreamToBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Organization(
    organization: OrganizationDTO,
    navigateToSelectedOrganization: (Long) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(whiteColor),
        onClick = {
            navigateToSelectedOrganization(organization.idOrganization!!)
        }
    ) {
        val lazyListState = rememberLazyListState()
        val loader = ImageLoader(LocalContext.current)
        Column(
        ) {
                //if (!organization.images.isNullOrEmpty())
                    AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://cdn.fishki.net/upload/post/2019/05/17/2980924/1-depositphotos-11856682-l-2015.jpg")
                                .build(),
                        contentDescription = "organization_image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                    )
               /* else
                    Image(
                        painter = painterResource(id = R.drawable.cat_money),
                        contentDescription = "organization_image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                    )*/

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(whiteColor)
            ) {
                Row(
                    modifier = Modifier.padding(top = 6.dp, start = 14.dp)
                ) {
                    Column(Modifier.weight(1f)) {

                        Text(
                            text = organization.name,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(8.dp))

                        /*LazyRow(state = lazyListState){
                            items(organization.categories!!){
                                Button(onClick = { *//*TODO*//* },
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.height(30.dp)){
                                    Text(text = it, fontSize = 12.sp)
                                }
                            }

                        }*/
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
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }


                        Spacer(Modifier.height(8.dp))

                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = "10:00-22:00",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.labelMedium
                        )
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

@Preview(name = "Preview1", device = "id:pixel_xl", showSystemUi = true)
@Composable
fun PreviewOrg(){
    /*Organization(
        organization = OrganizationDTO(
            1,
            "Ресторан",
            "аввыа",
            "89352536665",
            "London",
            "О чём-то",
            listOf(
                "Блины",
                "Пиво"
            ),
            5.0,
            5,
            null
        ),
    )*/
}