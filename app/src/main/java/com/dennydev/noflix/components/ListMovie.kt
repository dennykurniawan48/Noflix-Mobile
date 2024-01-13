package com.dennydev.noflix.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.noflix.R
import com.dennydev.noflix.model.response.movies.Movy
import com.dennydev.noflix.navigation.Screen

@Composable
fun ListMovie(
    navController: NavHostController,
    data: Movy
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = data.genre, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(modifier=Modifier.fillMaxWidth()){
            items(data.data){
                Card(elevation = 10.dp,modifier= Modifier
                    .width(130.dp)
                    .padding(4.dp)
                    .clickable {
                        navController.navigate(
                            Screen.MovieScreen.route.replace(
                                "{id}",
                                it.slug
                            )
                        )
                    }) {
                    Column(modifier=Modifier.fillMaxWidth()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = it.thumbnail),
                            contentDescription = "Image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                        Column(modifier= Modifier
                            .fillMaxWidth()
                            .padding(6.dp)) {
                            Text(text = it.title, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.height(40.dp), maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = it.release_year, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}