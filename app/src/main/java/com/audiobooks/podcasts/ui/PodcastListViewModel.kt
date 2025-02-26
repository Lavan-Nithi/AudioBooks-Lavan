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
    // TODO - Make the API call using repository.getPodcasts() and update the UI

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
        _isLoading.value = true
        viewModelScope.launch {
            val podcast = repository.getPodcasts()
            podcast.onSuccess { podcastList -> _podcasts.value = podcastList
            _errorMessage.value = ""}.onFailure { e -> _errorMessage.value = e.message ?: "Unknown Error Here"}
            _isLoading.value = false
        }
    }

}
