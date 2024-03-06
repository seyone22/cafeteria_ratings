package com.seyone22.cafeteriaRatings.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier,
    dataStoreManager: DataStoreManager,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current
    val currentSessionRatings by viewModel.currentSessionRatings.collectAsState()

    Scaffold(
    ) { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(240.dp))
                Text(
                    text = "How did we do?",
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    viewModel.ratingsList.forEach{ rating ->
                        var isButtonClicked by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                isButtonClicked = !isButtonClicked
                                viewModel.recordRating(rating)

                                Toast.makeText(
                                    context,
                                    "Thank you for your feedback!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            },
                            modifier = Modifier
                                .width(240.dp)
                                .height(240.dp)
                        ) {
                            Icon(
                                imageVector = rating.icon,
                                contentDescription = rating.name,
                                modifier = modifier
                                    .width(160.dp)
                                    .height(160.dp)
                            )
                        }
                    }
                }
                Text(text = "Number of Ratings: ${currentSessionRatings.ratingCount}")
                Text(text = "Total Rating Value: ${currentSessionRatings.totalRatingScore}")
                if (currentSessionRatings.ratingCount != 0) {
                    Text(text = "Average Rating Value: ${(currentSessionRatings.totalRatingScore/currentSessionRatings.ratingCount)}")
                }
            }
        }
    }
}

