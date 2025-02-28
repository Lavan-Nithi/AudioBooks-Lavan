package com.audiobooks.podcasts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

@Composable
fun PodcastListScreen(onShowDetails: (podcast: Podcast) -> Unit) {
    val viewModel: PodcastListViewModel = viewModel()
    val podcasts by viewModel.podcasts.collectAsState()
    // This fetches the podcasts from the API request
    LaunchedEffect(Unit) {
        viewModel.fetchPodcasts()
    }
    // Only displays PodcastListUI if the fetch is successful
    // Can add a loading state to inform the users that the podcasts are still being fetched
    // Can add an error state for failed fetches such as no internet connection
    if (podcasts.isNotEmpty()){
        PodcastListUI(
            onShowDetails = onShowDetails,
            podcasts = podcasts
        )
    }



}

@Composable
private fun PodcastListUI(
    onShowDetails: (podcast: Podcast) -> Unit,
    podcasts: List<Podcast>
) {
    Column {
        Image(painter = painterResource(R.drawable.audiobooks), contentDescription = "Audiobooks.com", Modifier.fillMaxWidth(), alignment = Alignment.Center)
        Text(text = "Podcasts", fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(10.dp))
        LazyColumn {
           items(podcasts){ podcast ->
               // Each podcast will be using PodcastRow Composable
               // This keeps the composable reusable
               PodcastRow(podcast = podcast, onShowDetails = onShowDetails)
               HorizontalDivider(color = Color.LightGray, modifier = Modifier.padding(start = 16.dp))

            }
        }
    }
}

@Composable
fun PodcastRow(podcast: Podcast, onShowDetails: (podcast: Podcast) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onShowDetails(podcast) }.padding(16.dp)
    ){
        AsyncImage(
            modifier = Modifier.size(85.dp).clip(RoundedCornerShape(16.dp)),
            model = podcast.image,
            contentDescription = podcast.title
        )
        Column(modifier = Modifier.padding(8.dp)){
            Text(text = podcast.title, fontWeight = FontWeight.Bold)
            Text(text = podcast.publisher, color = Color.Gray)
            // TO DO: if onFavourited is implemented the red text favourited will be rendered for podcast that are favourited
            if (podcast.favourite){
                Text(text = "Favourited", color = Color.Red)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PodcastListUIPreview() {
    PodcastsTheme {
        PodcastListUI(
            onShowDetails = {},
            podcasts = emptyList()
        )
    }
}
