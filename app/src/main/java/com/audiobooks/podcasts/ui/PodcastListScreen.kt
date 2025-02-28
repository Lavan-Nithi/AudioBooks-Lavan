package com.audiobooks.podcasts.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

@Composable
fun PodcastListScreen(onShowDetails: (podcast: Podcast) -> Unit) {
    // TODO - Implement the ViewModel to fetch the list of podcasts and update the UI
    // TODO - Modify this file as needed
    // TODO - Coil dependency was added as the image loader for the podcast image - feel free to use any other image loader
    val viewModel: PodcastListViewModel = viewModel()
    val best by viewModel.podcasts.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchPodcasts()
    }
    if (best.isNotEmpty()){
        PodcastListUI(
            onShowDetails = onShowDetails,
            podcasts = best
        )
    }



}

@Composable
private fun PodcastListUI(
    onShowDetails: (podcast: Podcast) -> Unit,
    podcasts: List<Podcast>
) {
    // TODO - Example UI layout - Modify to implement the requested UI
    Column {
        Text(text = "Podcasts", fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(8.dp))
        LazyColumn {
           items(podcasts){ podcast ->
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
