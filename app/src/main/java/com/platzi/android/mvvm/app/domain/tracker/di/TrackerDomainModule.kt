package com.platzi.android.mvvm.app.domain.tracker.di

import com.platzi.android.mvvm.app.domain.tracker.repository.TrackerRepository
import com.platzi.android.mvvm.app.domain.tracker.use_case.CalculateMealNutrientsUseCase
import com.platzi.android.mvvm.app.domain.tracker.use_case.GetFoodsForDateUseCase
import com.platzi.android.mvvm.app.domain.tracker.use_case.SearchFoodUseCase
import com.platzi.android.mvvm.app.domain.tracker.use_case.TrackFoodUseCase
import com.platzi.android.mvvm.app.domain.tracker.use_case.TrackerUseCase
import com.platzi.android.mvvm.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {
    @Provides
    @ViewModelScoped
    fun provideTrackerUseCase(
        trackerRepository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCase {
        return TrackerUseCase(
            searchFoodUseCase = SearchFoodUseCase(trackerRepository),
            trackFoodUseCase = TrackFoodUseCase(trackerRepository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(trackerRepository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
    }
}