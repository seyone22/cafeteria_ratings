package com.seyone22.cafeteriaRatings.ui.screen.settings

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.seyone22.cafeteriaRatings.R
import com.seyone22.cafeteriaRatings.calculateInitialDelay
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.data.SecureDataStoreManager
import com.seyone22.cafeteriaRatings.data.copyImageToAppDirectory
import com.seyone22.cafeteriaRatings.data.externalApi.ExternalApi
import com.seyone22.cafeteriaRatings.getCurrentTimeInISO8601
import com.seyone22.cafeteriaRatings.model.RatingsStore
import com.seyone22.cafeteriaRatings.model.Review
import com.seyone22.cafeteriaRatings.ui.AppViewModelProvider
import com.seyone22.cafeteriaRatings.ui.navigation.NavigationDestination
import com.seyone22.cafeteriaRatings.ui.workers.PostEmailWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
import java.io.FileWriter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object SettingsDetailDestination : NavigationDestination {
    override val route = "SettingsDetail"
    override val titleRes = R.string.app_name
    override val routeId = 15
    override val icon = Icons.Default.Settings
    override val iconFilled = Icons.Filled.Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDetailScreen(
    modifier: Modifier = Modifier,
    navigateToScreen: (screen: String) -> Unit,
    dataStoreManager: DataStoreManager,
    navigateBack: () -> Unit,
    backStackEntry: String,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = { Text(text = backStackEntry) },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier.padding(innerPadding)
        ) {
            when (backStackEntry) {
                "Connections" -> {
                    ConnectionsSettingsList(
                    )
                }

                "Appearance" -> {
                    AppearanceSettingsList(
                        viewModel = viewModel,
                        dataStoreManager = dataStoreManager
                    )
                }

                "Data" -> {
                    DataSettingsList(
                        viewModel = viewModel,
                        dataStoreManager = dataStoreManager
                    )
                }

                "About" -> {
                    AboutList()
                }
            }
        }
    }
}

@Composable
fun ConnectionsSettingsList(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    secureDataStoreManager: SecureDataStoreManager = SecureDataStoreManager(LocalContext.current),
    dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current),
    context: Context = LocalContext.current
) {
    var allowAutomaticUpdates by remember { mutableStateOf(isWorkScheduled(context)) }
    var editKey by remember { mutableStateOf(false) }
    var key by remember { mutableStateOf("") }
    var site by remember { mutableStateOf("") }
    var editSite by remember { mutableStateOf(false) }
    var serverAddress by remember { mutableStateOf("") }
    var editServerAddress by remember { mutableStateOf(false) }
    var desiredHour = 0
    var desiredMinute = 0

    LaunchedEffect(Unit) {
        allowAutomaticUpdates =
            secureDataStoreManager.getFromDataStore("ALLOW_AUTO").first().toString().toBoolean()
        site =
            secureDataStoreManager.getFromDataStore("SITE").first().toString()
        serverAddress = secureDataStoreManager.getFromDataStore("SERVER_ADDRESS").first().toString()
        key = maskString(secureDataStoreManager.getFromDataStore("API_KEY").first().toString(), 5)
        val desiredHourString =
            secureDataStoreManager.getFromDataStore("DESIRED_HOUR").first().toString()
        desiredHour = if (desiredHourString.isEmpty()) 17 else desiredHourString.toInt()
        val desiredMinuteString =
            secureDataStoreManager.getFromDataStore("DESIRED_MINUTE").first().toString()
        desiredMinute = if (desiredHourString.isEmpty()) 0 else desiredHourString.toInt()
    }


    Column {
        // Setting to enable automatic server pushes
        ListItem(
            headlineContent = { Text(text = "Send data to Server") },
            supportingContent = {
                Text(text = "Sends daily rating data to server automatically")
            },
            trailingContent = {
                Switch(checked = allowAutomaticUpdates, onCheckedChange = {
                    allowAutomaticUpdates = !allowAutomaticUpdates
                    coroutineScope.launch {
                        secureDataStoreManager.saveToDataStore(
                            "ALLOW_AUTO",
                            allowAutomaticUpdates.toString()
                        )
                    }
                    if (allowAutomaticUpdates) {
                        scheduleRatingEmail(context, desiredHour, desiredMinute)
                        coroutineScope.launch {
                            secureDataStoreManager.saveToDataStore(
                                "DESIRED_HOUR",
                                desiredHour.toString()
                            )
                            secureDataStoreManager.saveToDataStore(
                                "DESIRED_MINUTE",
                                desiredMinute.toString()
                            )
                        }

                    } else {
                        WorkManager.getInstance(context).cancelAllWork()
                    }
                })
            },
            modifier = Modifier.clickable {
                allowAutomaticUpdates = !allowAutomaticUpdates
                coroutineScope.launch {
                    secureDataStoreManager.saveToDataStore(
                        "ALLOW_AUTO",
                        allowAutomaticUpdates.toString()
                    )
                }
            }
        )
        // Setting to set Site
        ListItem(
            headlineContent = { Text(text = "Site Name") },
            supportingContent = {
                Text(text = site)
            },
            modifier = Modifier.clickable {
                editSite = true
            }
        )
        // Setting to change API Key
        ListItem(
            headlineContent = { Text(text = "API Key") },
            supportingContent = {
                Text(text = key)
            },
            modifier = Modifier.clickable {
                editKey = true
            }
        )
        // Setting to change Server Location
        ListItem(
            headlineContent = { Text(text = "Server Address") },
            supportingContent = {
                Text(text = "https://survey.etsteas.co.uk/api/reviews")
            },
            modifier = Modifier.clickable {
                editServerAddress = true
            }
        )
        // Send today's data to the Server
        ListItem(
            headlineContent = { Text(text = "Send Data") },
            supportingContent = {
                Text(text = "Send collected data to the server manually")
            },
            modifier = Modifier.clickable {
                coroutineScope.launch {
                    val pushData = withContext(Dispatchers.IO) {
                        try {
                            val ratings =
                                true
                        } catch (e: Exception) {
                            if (e is HttpException) {
                                val errorBody = e.response()?.errorBody()?.string()
                                Log.e("TAG", "Error Body: $errorBody")
                            } else {
                                Log.e("TAG", "$e")
                            }
                        }
                    }
                }
            }
        )
    }

    if (editSite) {
        Dialog(
            onDismissRequest = { editSite = false },
        ) {
// Draw a rectangle shape with rounded corners inside the dialog
            var newSite by remember { mutableStateOf(site) }
            val focusManager = LocalFocusManager.current

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(235.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Enter the Site name (All Caps)",
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(0.dp, 8.dp),
                        value = newSite,
                        onValueChange = {
                            newSite = it
                        },
                        label = { Text("Site Name") },
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.moveFocus(
                                FocusDirection.Next
                            )
                        })
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { editSite = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = {
                                site = newSite
                                coroutineScope.launch {
                                    secureDataStoreManager.saveToDataStore("SITE", site)
                                    editSite = false
                                }
                            },
                            modifier = Modifier.padding(8.dp),
                            enabled = newSite.isNotBlank()
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
    if (editKey) {
        Dialog(
            onDismissRequest = { editKey = false },
        ) {
// Draw a rectangle shape with rounded corners inside the dialog
            var newKey by remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(235.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Enter New API Key",
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(0.dp, 8.dp),
                        value = newKey,
                        onValueChange = {
                            newKey = it
                        },
                        label = { Text("API Key") },
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.moveFocus(
                                FocusDirection.Next
                            )
                        })
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { editKey = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = {
                                key = newKey
                                coroutineScope.launch {
                                    secureDataStoreManager.saveToDataStore("API_KEY", key)
                                    editKey = false
                                }
                            },
                            modifier = Modifier.padding(8.dp),
                            enabled = newKey.isNotBlank()
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
    Toast.makeText(
        context,
        "Please contact the developer to modify this.",
        Toast.LENGTH_SHORT
    )
        .show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataSettingsList(
    viewModel: SettingsViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    dataStoreManager: DataStoreManager,
    context: Context = LocalContext.current
) {
    var showDebug by remember { mutableStateOf(false) }
    var ratingsStore: RatingsStore = RatingsStore()
    LaunchedEffect(Unit) {
        showDebug = dataStoreManager.getFromDataStore("SHOW_DEBUG").first().toString().toBoolean()
        ratingsStore =
            dataStoreManager.getFromDataStore("RATINGS").first() as RatingsStore
    }
    Column {
        // Resets rating data
        ListItem(
            headlineContent = { Text(text = "Reset Rating Data") },
            supportingContent = {
                Text(text = "Reset the collected ratings for the day.")
            },
            modifier = Modifier.clickable {
                try {
                    coroutineScope.launch {
                        dataStoreManager.saveRatingToDataStore(RatingsStore(0, 0f))
                    }
                    Toast.makeText(
                        context,
                        "Data successfully deleted!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } catch (_: Exception) {

                }
            }
        )
        // Enable custom background
        ListItem(
            headlineContent = { Text(text = "Show Debug") },
            supportingContent = {
                Text(text = "Shows collected data on dashboard for debug purposes")
            },
            trailingContent = {
                Switch(checked = showDebug, onCheckedChange = {
                    showDebug = !showDebug
                    coroutineScope.launch {
                        dataStoreManager.saveToDataStore(
                            "SHOW_DEBUG",
                            showDebug.toString()
                        )
                    }
                })
            },
            modifier = Modifier.clickable {
                showDebug = !showDebug
                coroutineScope.launch {
                    dataStoreManager.saveToDataStore(
                        "SHOW_DEBUG",
                        showDebug.toString()
                    )
                }
            }
        )
        // Export to .csv
        ListItem(
            headlineContent = { Text(text = "Export Data") },
            supportingContent = {
                Text(text = "Exports Collected data to a .csv file.")
            },
            modifier = Modifier.clickable {
                try {
                    val fileName = "log.csv" // replace with your actual file name
                    val file = File(context.getExternalFilesDir(null), fileName)

                    if (!file.exists()) {
                        // If the file doesn't exist, create it and write the header
                        file.createNewFile()
                        FileWriter(file, true).use { writer ->
                            writer.append("timestamp,count,total,satisfied,neutral,dissatisfied\n")
                        }
                    }
                    coroutineScope.launch {
                        ratingsStore =
                            dataStoreManager.getFromDataStore("RATINGS").first() as RatingsStore
                        // Now, append the new scan data
                        FileWriter(file, true).use { writer ->
                            val rowData =
                                "${getCurrentTimeInISO8601()},${ratingsStore.ratingCount},${ratingsStore.totalRatingScore},${ratingsStore.countSatisfied},${ratingsStore.countNeutral},${ratingsStore.countDissatisfied}\n"
                            writer.append(rowData)
                        }
                        Toast.makeText(
                            context,
                            "Data successfully exported!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (_: Exception) {

                }
            }
        )
        // View Log File
        ListItem(
            headlineContent = { Text(text = "Open Log file") },
            modifier = Modifier.clickable {
                val fileName = "log.csv" // Replace with your CSV file name
                val file = File(context.getExternalFilesDir(null), fileName)

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW)
                    .setDataAndType(uri, "text/csv")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                // Check if there's an app to handle the intent before starting the activity
                if(file.exists()) {
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        // Handle the case when no activity is found
                        Toast.makeText(
                            context,
                            "Please check if you have an app that can handle .csv files",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    // Handle the case when no activity is found
                    Toast.makeText(
                        context,
                        "Log file does not exist. Scan some QR Codes first!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceSettingsList(
    viewModel: SettingsViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    dataStoreManager: DataStoreManager
) {
    val context = LocalContext.current

    var editTheme by remember { mutableStateOf(false) }
    var useCustomBackground by remember { mutableStateOf(false) }
    var editGreeting by remember { mutableStateOf(false) }
    var backgroundUri by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        backgroundUri = dataStoreManager.getFromDataStore("BACKGROUND_URI").first().toString()
        greeting = dataStoreManager.getFromDataStore("HOME_GREETING").first().toString()
        useCustomBackground =
            dataStoreManager.getFromDataStore("USE_CUSTOM_BACKGROUND").first().toString()
                .toBoolean()
    }

    // ActivityResultLauncher for image selection
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        // Handle the selected image URI
        coroutineScope.launch {
            backgroundUri = uri.toString()
            dataStoreManager.saveToDataStore("BACKGROUND_URI", backgroundUri)
            copyImageToAppDirectory(context, backgroundUri.toUri())
        }
    }
    Column {
/*        // Setting to change Application Theme. DOES NOT WORK
        ListItem(
            headlineContent = { Text(text = "Theme") },
            supportingContent = {
                if (isSystemInDarkTheme()) {
                    Text(text = "Dark")
                } else {
                    Text(text = "Light")
                }
            },
            modifier = Modifier.clickable { editTheme = !editTheme }
        )*/
        // Setting to change Home Screen Greeting
        ListItem(
            headlineContent = { Text(text = "Home Screen Greeting") },
            supportingContent = {
                Text(text = greeting)
            },
            modifier = Modifier.clickable {
                editGreeting = true
            }
        )
        // Enable custom background
        ListItem(
            headlineContent = { Text(text = "Custom Background") },
            supportingContent = {
                Text(text = "Use a custom background image on the home screen")
            },
            trailingContent = {
                Switch(checked = useCustomBackground, onCheckedChange = {
                    useCustomBackground = !useCustomBackground
                    coroutineScope.launch {
                        dataStoreManager.saveToDataStore(
                            "USE_CUSTOM_BACKGROUND",
                            useCustomBackground.toString()
                        )
                    }
                })
            },
            modifier = Modifier.clickable {
                useCustomBackground = !useCustomBackground
                coroutineScope.launch {
                    dataStoreManager.saveToDataStore(
                        "USE_CUSTOM_BACKGROUND",
                        useCustomBackground.toString()
                    )
                }
            }
        )
        // Setting to change Home Screen Background Image
        ListItem(
            headlineContent = { Text(text = "Home Screen Background Image") },
            supportingContent = {
                Text(text = backgroundUri)
            },
            modifier = if (useCustomBackground) {
                Modifier.clickable {
                    launcher.launch("image/*")
                }
            } else {
                Modifier
            }
        )

        if (editGreeting) {
            Dialog(
                onDismissRequest = { editGreeting = false },
            ) {
// Draw a rectangle shape with rounded corners inside the dialog
                var newGreeting by remember { mutableStateOf(greeting) }
                val focusManager = LocalFocusManager.current

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp, 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Enter New Greeting",
                            style = MaterialTheme.typography.titleLarge
                        )
                        OutlinedTextField(
                            modifier = Modifier.padding(0.dp, 8.dp),
                            value = newGreeting,
                            onValueChange = {
                                newGreeting = it
                            },
                            label = { Text("Greeting") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.moveFocus(
                                    FocusDirection.Next
                                )
                            })
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            TextButton(
                                onClick = { editGreeting = false },
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text("Dismiss")
                            }
                            TextButton(
                                onClick = {
                                    greeting = newGreeting
                                    coroutineScope.launch {
                                        dataStoreManager.saveToDataStore("HOME_GREETING", greeting)
                                        editGreeting = false
                                    }
                                },
                                modifier = Modifier.padding(8.dp),
                                enabled = newGreeting.isNotBlank()
                            ) {
                                Text("Confirm")
                            }
                        }
                    }
                }
            }
        }

        // Edit Theme Dialog
        if (editTheme) {
            var selectedTheme by remember { mutableIntStateOf(2) }

            Dialog(
                onDismissRequest = { editTheme = !editTheme },
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = "Theme",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(0.dp, 8.dp)
                        )
                        Row(

                        ) {
                            RadioButton(
                                enabled = false,
                                selected = (selectedTheme == 0),
                                onClick = {
                                    selectedTheme = 0
                                })
                            Text(text = "Light")
                        }
                        Row {
                            RadioButton(
                                enabled = false,
                                selected = (selectedTheme == 1),
                                onClick = { selectedTheme = 1 })
                            Text(text = "Dark")
                        }
                        Row {
                            RadioButton(
                                enabled = false,
                                selected = (selectedTheme == 2),
                                onClick = { selectedTheme = 2 })
                            Text(text = "System Default")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutList() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 24.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.english_tea_shop_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )
    }
    HorizontalDivider()
    Column {
        ListItem(
            headlineContent = { Text(text = "Version") },
            supportingContent = {
                Text(
                    text = "${stringResource(id = R.string.app_version)} (${
                        stringResource(
                            id = R.string.release_date
                        )
                    } | ${stringResource(id = R.string.release_time)})"
                )
            },
            modifier = Modifier.clickable { }
        )
    }
}

fun maskString(input: String, visibleChars: Int): String {
    val length = input.length
    return if (length <= visibleChars) {
        input
    } else {
        "*".repeat(length - visibleChars) + input.substring(length - visibleChars)
    }
}


fun scheduleRatingEmail(context: Context, desiredHour: Int, desiredMinute: Int) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val dailyWorkRequest = PeriodicWorkRequestBuilder<PostEmailWorker>(
        repeatInterval = 1,
        repeatIntervalTimeUnit = TimeUnit.DAYS
    )
        .setConstraints(constraints)
        .setInitialDelay(calculateInitialDelay(desiredHour, desiredMinute), TimeUnit.MILLISECONDS)
        .addTag("push_to_server")
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "PushToServer",
        ExistingPeriodicWorkPolicy.KEEP,
        dailyWorkRequest
    )
}

fun isWorkScheduled(context: Context): Boolean {
    val workManager = WorkManager.getInstance(context)
    val workInfoListenableFuture = workManager.getWorkInfosByTag("push_to_server")
    var result: Boolean = false
    workInfoListenableFuture.addListener(Runnable {
        val workInfoList = workInfoListenableFuture.get()
        for (workInfo in workInfoList) {
            result = workInfo.state == WorkInfo.State.ENQUEUED ||
                    workInfo.state == WorkInfo.State.RUNNING
        }
    }, Executors.newSingleThreadExecutor())
    return result
}