package com.platzi.android.mvvm.presentation.tracker_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.platzi.android.mvvm.app.domain.tracker.use_case.TrackerUseCase
import com.platzi.android.mvvm.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel  @Inject constructor(
    preferences: Preferences,
    private val trackerUseCase: TrackerUseCase
): ViewModel() {
    init {
        preferences.saveShouldShowOnboarding(true)
        executeSearch()
    }

    private fun executeSearch() {
        viewModelScope.launch {
            trackerUseCase.searchFoodUseCase("egg")
        }
    }
}