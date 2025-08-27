package com.platzi.android.mvvm.presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.platzi.android.mvvm.app.R
import com.platzi.android.mvvm.app.ui.theme.LocalSpacing
import com.platzi.android.mvvm.app.ui.theme.PlatziCaloriesTheme
import com.platzi.android.mvvm.presentation.tracker_overview.components.AddButton
import com.platzi.android.mvvm.presentation.tracker_overview.components.DaySelector
import com.platzi.android.mvvm.presentation.tracker_overview.components.ExpandableMeal
import com.platzi.android.mvvm.presentation.tracker_overview.components.NutrientHeader
import com.platzi.android.mvvm.presentation.tracker_overview.components.TrackedFoodItem
import com.platzi.android.mvvm.presentation.tracker_overview.model.defaultMeals
import java.time.LocalDate

@Composable
fun TrackerOverviewScreen(
    trackerViewModel: TrackerOverviewViewModel = hiltViewModel(),
    onNavigate: (String, Int, Int, Int) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state = trackerViewModel.state

    LazyColumn(modifier = Modifier.fillMaxSize().padding(bottom = spacing.spaceMedium)) {
        item {
            NutrientHeader(trackerViewModel.state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = LocalDate.now(),
                onPreviousDayClick = {

                },
                onNextDayClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    trackerViewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceSmall)
                ) {
                    val foods = state.trackedFoods.filter {
                        it.mealType == meal.mealType
                    }
                    foods.forEach { food ->
                        TrackedFoodItem(
                            trackedFood = food
                        ) {}
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    AddButton(
                        text = stringResource(
                            id = R.string.add_meal,
                            meal.name.asString(context)
                        ),
                        onClick = {
                            onNavigate(meal.name.asString(context), state.date.dayOfMonth, state.date.monthValue, state.date.year)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
