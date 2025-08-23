package com.platzi.android.mvvm.presentation.tracker_overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.platzi.android.mvvm.app.ui.theme.LocalSpacing
import com.platzi.android.mvvm.app.ui.theme.PlatziCaloriesTheme

@Composable
fun NutrientHeader(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    val animatedCalorieCount = animateIntAsState(targetValue = 2000)

    Column(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.onSurfaceVariant)
        .padding(horizontal = spacing.spaceLarge, vertical = spacing.spaceExtraLarge)
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutrientsBarInfo(
                100,
                200,
                "Carbs",
                MaterialTheme.colorScheme.tertiary,
                Modifier.size(90.dp)
            )
            NutrientsBarInfo(
                50,
                150,
                "Proteins",
                MaterialTheme.colorScheme.error,
                Modifier.size(90.dp)
            )
            NutrientsBarInfo(
                300,
                500,
                "Fats",
                MaterialTheme.colorScheme.primary,
                Modifier.size(90.dp)
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            UnitDisplay(
                amount = animatedCalorieCount.value,
                unit = "kcal",
                amountColor = MaterialTheme.colorScheme.onPrimary,
                amountTextSize = 40.sp,
                unitColor = MaterialTheme.colorScheme.onPrimary
            )
            Column {
                Text(
                    text = "Your goal: ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                UnitDisplay(
                    amount = 2550,
                    unit = "kcal",
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextSize = 40.sp,
                    unitColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        NutrientsBar(
            carbs = 100,
            protein = 200,
            fat = 100,
            calories = 2000,
            calorieGoal = 3500,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NutrientHeaderPreview() {
    PlatziCaloriesTheme {
        NutrientHeader()
    }
}
