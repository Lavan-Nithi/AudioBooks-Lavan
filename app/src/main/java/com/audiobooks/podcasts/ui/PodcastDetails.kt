package com.audiobooks.podcasts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

@Composable
fun PodcastDetailsScreen(podcast: Podcast, onBack: () -> Unit) {
    // TODO Implement the requested UI
    var isFavourited by remember { mutableStateOf(podcast.favourite)}
    Column(
        modifier = Modifier.padding(12.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clickable{ onBack() }){
            Icon(painter = painterResource(id = R.drawable.arrow_back), tint = Color.Black, contentDescription = null, modifier = Modifier.size(20.dp))
            Text(text = "Back", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold)
        }
        Text(text = podcast.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp), textAlign = TextAlign.Center)
        Text(text = podcast.publisher, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp), textAlign = TextAlign.Center)
        AsyncImage(model = podcast.image, contentDescription = podcast.title, modifier = Modifier.height(225.dp).clip(RoundedCornerShape(16.dp)))
        Button(onClick = {isFavourited = !isFavourited}, shape = RectangleShape, modifier = Modifier.padding(top = 12.dp).clip(
            RoundedCornerShape(16.dp)), colors = ButtonColors(containerColor = if(isFavourited) Color.Gray else Color(0xFFFF525D), contentColor = if(isFavourited) Color.Black else Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Red)
        ){
            Text(if (isFavourited) "Favourited" else "Favourite")
            if (isFavourited) {
            Icon(
                Icons.Filled.Done,
                contentDescription = "checkmark",
                modifier = Modifier.padding(start = 4.dp)
            )
            }
        }
        Text(text = podcast.description, fontSize = 14.sp, color = Color.DarkGray, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
    }

}

@Preview(showBackground = true)
@Composable
private fun PodcastDetailsScreenPreview() {
    PodcastsTheme {
        PodcastDetailsScreen(
            podcast = Podcast(
                title = "Example Podcast Title",
                description="The Ed Mylett Show showcases the greatest peak-performers across all industries in one place",
                id="abc",
                image="https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
                publisher="Podcast Publisher",
                favourite = false
            ),
            onBack = {}
        )
    }
}
