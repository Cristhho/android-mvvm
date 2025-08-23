package com.platzi.android.mvvm.presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.platzi.android.mvvm.app.R
import com.platzi.android.mvvm.app.ui.theme.PlatziCaloriesTheme

@Composable
fun NutrientsBarInfo(
    value: Int,
    goal: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colorScheme.onSurfaceVariant
    val goalExeededColor = MaterialTheme.colorScheme.error
    val angleRatio = remember {
        Animatable(0f)
    }
    LaunchedEffect(value) {
        angleRatio.animateTo(
            targetValue = if (goal > 0) value / goal.toFloat() else 0f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            drawRoundRect(
                color = if (value <= goal) background else goalExeededColor,
                size = size,
                cornerRadius = CornerRadius.Zero
            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            UnitDisplay(
                amount = value,
                unit = stringResource(id = R.string.grams),
                amountColor = if (value <= goal) MaterialTheme.colorScheme.onSecondary else goalExeededColor,
                unitColor = if (value <= goal) MaterialTheme.colorScheme.onSecondary else goalExeededColor,
            )
            Text(
                text = name,
                color = if (value <= goal) MaterialTheme.colorScheme.onSecondary else goalExeededColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NutrientsBarInfoPreview() {
    PlatziCaloriesTheme {
        NutrientsBarInfo(
            100,
            200,
            "Carbs",
            Color.Red,
            modifier = Modifier.size(90.dp)
        )
    }
}
