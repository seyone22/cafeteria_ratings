plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

android {
    namespace = "com.seyone22.cafeteriaRatings"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.seyone22.cafeteriaRatings"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
// AndroidX Core
    implementation("androidx.core:core-ktx:1.12.0")

// AndroidX Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

// AndroidX Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")

// AndroidX Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

// AndroidX Compose Material3
    implementation("androidx.compose.material3:material3:1.2.0")

// JUnit
    testImplementation("junit:junit:4.13.2")

// AndroidX Test
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

// AndroidX Compose Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

// AndroidX Compose Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.6.2")

// AndroidX Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

// AndroidX Compose Material3 Adaptive Navigation Suite
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha04")

// AndroidX Compose Material3 Window Size Class Android
    implementation("androidx.compose.material3:material3-window-size-class-android:1.2.0")

// AndroidX Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

// AndroidX Lifecycle ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

// Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Retrofit with Kotlin Serialization Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

// Kotlinx Serialization JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

// No clue what this does
    implementation("androidx.window:window:1.2.0")

//WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

// Secure Datastore
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
}