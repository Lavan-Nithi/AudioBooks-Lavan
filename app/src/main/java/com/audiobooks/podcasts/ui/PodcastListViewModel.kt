package com.audiobooks.podcasts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.network.PodcastRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PodcastListViewModel : ViewModel() {
    private val repository = PodcastRepository()
    private val _podcasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts: StateFlow<List<Podcast>> get() = _podcasts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMassage: StateFlow<String> get() = _errorMessage


    init {
        fetchPodcasts()

    }
    fun fetchPodcasts(){
        // Loading state indicates that the podcasts are currently being fetched
        _isLoading.value = true
        // Coroutine allows for asynchronous execution when the podcasts are being fetched
        // We do this so that we don't freeze the UI
        viewModelScope.launch {
            val podcast = repository.getPodcasts()
            // onSuccess we populate the podcasts field with the fetched values
            podcast.onSuccess { podcastList -> _podcasts.value = podcastList
                // onFailure sets the error message to the one we receive from the API or Unknown Error if error message does not exist
                _errorMessage.value = ""}.onFailure { e -> _errorMessage.value = e.message ?: "Unknown Error Here"}
            // Loading state is now false as we should have received either the lists of podcasts onSuccess or and error message if onFailure
            _isLoading.value = false
        }
    }

}
