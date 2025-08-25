package com.platzi.android.mvvm.app.domain.tracker.di

import com.platzi.android.mvvm.app.domain.tracker.repository.TrackerRepository
import com.platzi.android.mvvm.app.domain.tracker.use_case.SearchFoodUseCase
import com.platzi.android.mvvm.app.domain.tracker.use_case.TrackFoodUseCase
import com.platzi.android.mvvm.app.domain.tracker.use_case.TrackerUseCase
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
        trackerRepository: TrackerRepository
    ): TrackerUseCase {
        return TrackerUseCase(
            searchFoodUseCase = SearchFoodUseCase(trackerRepository),
            trackFoodUseCase = TrackFoodUseCase(trackerRepository)
        )
    }
}