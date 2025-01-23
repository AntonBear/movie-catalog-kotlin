package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anton.movie_catalog_kotlin.R
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun DividedSlider(
    initialValue: Float = 5f,
    onRatingChanged: (Int) -> Unit
) {
    var sliderValue by remember { mutableFloatStateOf(initialValue) }
    var sliderWidth by remember { mutableFloatStateOf(0f) }
    val gap = 9.dp
    var textWidth by remember { mutableIntStateOf(0) }
    val inactiveTrackColor = colorResource(id = R.color.dark_faded)
    var textHeight by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        Slider(
            value = sliderValue,
            onValueChange = { newValue ->
                sliderValue = newValue
                onRatingChanged(newValue.toInt())
            },
            valueRange = 0f..10f,
            steps = 10,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    sliderWidth = it.width.toFloat()
                }
                .drawBehind {
                    drawDivider(sliderValue, sliderWidth, gap, color = Color.Black)
                },
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.gradient_start),
                activeTrackColor = colorResource(R.color.gradient_start),
                inactiveTrackColor = colorResource((R.color.dark_faded)),
                activeTickColor = Color.White,
                inactiveTickColor = colorResource(R.color.gradient_start)


            )
        )
        Text(
            text = sliderValue.roundToInt().toString(),
            fontSize = 16.sp,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textWidth = coordinates.size.width
                    textHeight = coordinates.size.height
                }
                .offset {
                    val offset = (sliderValue / 10f) * sliderWidth
                    IntOffset(offset.roundToInt() - textWidth / 2, -110)
                }
                .drawBehind {
                    drawCircle(
                        color = inactiveTrackColor,
                        radius = max(textWidth, textHeight).toFloat() / 2 + 10.dp.toPx()
                    )
                },
            textAlign = TextAlign.Center,
            color = Color.White
        )


    }

}

private fun DrawScope.drawDivider(
    sliderValue: Float,
    sliderWidth: Float,
    gap: Dp,
    color: Color
) {
}









