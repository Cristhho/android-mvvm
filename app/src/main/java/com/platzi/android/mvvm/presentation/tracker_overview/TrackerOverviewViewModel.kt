package com.platzi.android.mvvm.presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.platzi.android.mvvm.app.domain.tracker.use_case.TrackerUseCase
import com.platzi.android.mvvm.core.domain.preferences.Preferences
import com.platzi.android.mvvm.core.domain.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel  @Inject constructor(
    preferences: Preferences,
    private val trackerUseCase: TrackerUseCase
): ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEven = _uiEvent.receiveAsFlow()

    private var getFoodForDateJob: Job? = null

    init {
        refreshFoods()
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when(event) {
            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if(it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCase.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }
            is TrackerOverviewEvent.OnNextDayClick -> {
                state = state.copy(date = state.date.plusDays(1))
                refreshFoods()
            }
            is TrackerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(date = state.date.minusDays(1))
                refreshFoods()
            }
        }
    }

    private fun refreshFoods() {
        getFoodForDateJob?.cancel()
        getFoodForDateJob = trackerUseCase
            .getFoodsForDateUseCase(state.date)
            .onEach { foods ->
                val nutrientsResult = trackerUseCase.calculateMealNutrientsUseCase(foods)
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProteins = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrientsResult.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}