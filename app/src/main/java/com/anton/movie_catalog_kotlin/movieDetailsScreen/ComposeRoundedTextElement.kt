package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.anton.movie_catalog_kotlin.R

@Composable
fun RoundedTextElement(text: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = colorResource(R.color.dark_faded),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraLight ), modifier = Modifier.padding(16.dp))
    }

}