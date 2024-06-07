package com.wayplaner.learn_room.feedbasks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.starColor
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun ReviewItem(userName: String, reviewText: String, rating: Int, date: String) {
    Card(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(backHome)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(modifier = Modifier.padding(bottom = 6.dp).fillMaxWidth()) {
                    Text(
                        text = userName,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = date,
                        color = grayList,
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                    )
                }

                Row {
                    for (i in 1..5) {
                        val starColor = if (i <= rating) starColor else Color.Gray
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(2.dp)
                        )

                    }
                }

                Text(
                    text = reviewText,
                    color = grayList,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
        }
    }
}

@Composable
fun ReviewsScreen(reviews: List<Review>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backOrgHome)
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {

            Text(
                text = "Отзывы",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "\"Ромашка\"",
                color = whiteColor,
                fontFamily = FontFamily(
                    Font(
                        R.font.ag_cooper_cyr,
                        FontWeight.Medium
                    )
                ),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            reviews.forEach { review ->
                ReviewItem(
                    userName = review.userName,
                    reviewText = review.reviewText,
                    rating = review.rating,
                    date = review.date
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .clip(MaterialTheme.shapes.small)
                .padding(top = 12.dp, start = 16.dp, bottom = 5.dp, end = 5.dp)
                .size(45.dp),
            containerColor = summRedColor,
            onClick = { /*navController.navigateUp()*/ }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                tint = whiteColor,
                modifier = Modifier.size(32.dp),
                contentDescription = "Добавить"
            )
        }
    }
}

data class Review(
    val userName: String,
    val reviewText: String,
    val rating: Int,
    val date: String
)

@Preview(showBackground = true)
@Composable
fun ReviewsScreenPreview() {
    val reviews = listOf(
        Review("Никита", "Отличное место!", 5, "29 мая 15:12"),
        Review("Владислав", "Все понравилось", 4, "29 мая 15:14"),
        Review("Никита", "Средне", 3, "1 июня 10:52")
    )
    ReviewsScreen(reviews)
}