package com.seyone22.cafeteriaRatings.ui.screen.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seyone22.cafeteriaRatings.R
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.data.foregroundColor
import com.seyone22.cafeteriaRatings.model.RatingsStore
import com.seyone22.cafeteriaRatings.ui.AppViewModelProvider
import com.seyone22.cafeteriaRatings.ui.navigation.NavigationDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

object HomeDestination : NavigationDestination {
    override val route = "Home"
    override val titleRes = R.string.app_name
    override val routeId = 0
    override val icon = Icons.Outlined.Home
    override val iconFilled = Icons.Filled.Home
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    navigateToScreen: (screen: String) -> Unit,
    dataStoreManager: DataStoreManager,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    goFullscreen: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val context = LocalContext.current
    var isFullscreen by remember { mutableStateOf(false) }
    var showDebug by remember { mutableStateOf(false) }

    val bitmap = getBitmapFromYourSource(context = context)?.asAndroidBitmap()
    var foreground = if (bitmap != null) {
        foregroundColor(bitmap)
    } else {
        Color.Black
    }
    var backgroundUri by remember { mutableStateOf("") }
    var useCustomBackground by remember { mutableStateOf(false) }
    var homeGreeting by remember { mutableStateOf("") }
    var homeGreetingTwo by remember { mutableStateOf("") }
    var homeGreetingThree by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        backgroundUri = dataStoreManager.getFromDataStore("BACKGROUND_URI").first().toString()
        useCustomBackground =
            dataStoreManager.getFromDataStore("USE_CUSTOM_BACKGROUND").first().toString()
                .toBoolean()
        homeGreeting = dataStoreManager.getFromDataStore("HOME_GREETING").first().toString()
            .ifBlank { "How was your meal?" }
        homeGreetingTwo = dataStoreManager.getFromDataStore("HOME_GREETING_TWO").first().toString()
            .ifBlank { "How was your meal?" }
        homeGreetingThree = dataStoreManager.getFromDataStore("HOME_GREETING_THREE").first().toString()
            .ifBlank { "How was your meal?" }
        showDebug = dataStoreManager.getFromDataStore("SHOW_DEBUG").first().toString().toBoolean()
    }

    if (!useCustomBackground) {
        foreground = Color.Black
    }

    if (isFullscreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (useCustomBackground) {
                val colorMatrix = ColorMatrix().apply {
                    setToScale(0.75f, 0.75f, 0.75f, 1f)
                }
                getBitmapFromYourSource(context = context)?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(
                                radiusX = 5.dp,
                                radiusY = 5.dp,
                                edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(0.dp))
                            ),
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.colorMatrix(colorMatrix)
                    )
                }
            }
            IconButton(
                onClick = {
                    goFullscreen()
                    isFullscreen = !isFullscreen
                },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Fullscreen,
                    tint = foreground,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .size(64.dp),
                )
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(120.dp))
                Icon(
                    painter = painterResource(id = R.drawable.english_tea_shop_logo),
                    contentDescription = null,
                    tint = foreground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 16.dp),
                )
                Text(
                    text = homeGreeting,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = foreground,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = homeGreetingTwo,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = foreground,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = homeGreetingThree,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = foreground,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    viewModel.ratingsList.forEach { rating ->
                        var isButtonClicked by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                isButtonClicked = !isButtonClicked
                                var success = false
                                coroutineScope.launch {
                                    success = viewModel.recordRating(rating, context)
                                }
                                if (success) {
                                    Toast.makeText(
                                        context,
                                        "Thank you for your feedback!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            },
                            modifier = Modifier
                                .width(240.dp)
                                .height(240.dp)
                        ) {
                            Text(
                                text = rating.emoji,
                                color = foreground,
                                fontSize = 130.sp,
                                modifier = modifier
                                    .width(160.dp)
                                    .height(160.dp)
                            )
                        }
                    }
                }
                if (showDebug) {
                    DebugData(viewModel = viewModel, foreground = foreground)
                }
            }
        }
    } else {
        Scaffold(
        ) { innerpadding ->
            Box(
                modifier = Modifier
                    .padding(innerpadding)
                    .fillMaxSize()
            ) {
                if (useCustomBackground) {
                    val colorMatrix = ColorMatrix().apply {
                        setToScale(0.75f, 0.75f, 0.75f, 1f)
                    }
                    getBitmapFromYourSource(context = context)?.let {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .blur(
                                    radiusX = 5.dp,
                                    radiusY = 5.dp,
                                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(0.dp))
                                ),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = ColorFilter.colorMatrix(colorMatrix)

                        )
                    }
                }
                IconButton(
                    onClick = {
                        goFullscreen()
                        isFullscreen = !isFullscreen
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        tint = foreground,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .size(64.dp),
                    )
                }
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(120.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.english_tea_shop_logo),
                        contentDescription = null,
                        tint = foreground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 16.dp),
                    )
                    Text(
                        text = homeGreeting,
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        color = foreground,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = homeGreetingTwo,
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        color = foreground,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = homeGreetingThree,
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        color = foreground,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        viewModel.ratingsList.forEach { rating ->
                            var isButtonClicked by remember { mutableStateOf(false) }
                            IconButton(
                                onClick = {
                                    isButtonClicked = !isButtonClicked
                                    var success = false
                                    coroutineScope.launch {
                                        success = viewModel.recordRating(rating, context)
                                        Log.d("TAG", "HomeScreen: $success")
                                    }
                                    if (success) {
                                        Toast.makeText(
                                            context,
                                            "Thank you for your feedback!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                },
                                modifier = Modifier
                                    .width(240.dp)
                                    .height(240.dp)
                            ) {
                                Text(
                                    text = rating.emoji,
                                    color = foreground,
                                    fontSize = 130.sp,
                                    modifier = modifier
                                        .width(160.dp)
                                        .height(160.dp)
                                )
                            }
                        }
                    }
                    if (showDebug) {
                        DebugData(viewModel = viewModel, foreground = foreground)
                    }
                }
            }
        }
    }
}

@Composable
fun getBitmapFromYourSource(context: Context): ImageBitmap? {
    return try {
        // Replace "image.jpg" with the actual file name or URI
        val imageFile = File(context.filesDir, "background_image.jpg")

        // Load image from the file
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

        // Convert the loaded bitmap to an ImageBitmap
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun DebugData(
    viewModel: HomeViewModel,
    foreground: Color,
    dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current)
) {
    val currentSessionRatings by viewModel.currentSessionRatings.collectAsState()

    LaunchedEffect(Unit) {
        val ratings = dataStoreManager.getFromDataStore("RATINGS").first() as RatingsStore

        viewModel.currentSessionRatings.value = viewModel.currentSessionRatings.value.copy(
            ratingCount = ratings.ratingCount,
            totalRatingScore = ratings.totalRatingScore,
            countSatisfied = ratings.countSatisfied,
            countNeutral = ratings.countNeutral,
            countDissatisfied = ratings.countDissatisfied
        )
    }

    Row {
        Column(
            modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
        ) {
            Text(
                text = "Number of Ratings: ${currentSessionRatings.ratingCount}",
                color = foreground
            )
            Text(
                text = "Total Rating Value: ${currentSessionRatings.totalRatingScore}",
                color = foreground
            )
            if (currentSessionRatings.ratingCount != 0) {
                Text(
                    text = "Average Rating Value: ${(currentSessionRatings.totalRatingScore / currentSessionRatings.ratingCount)}",
                    color = foreground
                )
            }
        }
        Column {
            Text(text = "Satisfied: ${currentSessionRatings.countSatisfied}", color = foreground)
            Text(text = "Neutral: ${currentSessionRatings.countNeutral}", color = foreground)
            Text(
                text = "Dissatisfied: ${currentSessionRatings.countDissatisfied}",
                color = foreground
            )
        }
    }
}