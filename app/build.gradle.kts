plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.e_commerce_project"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.e_commerce_project"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {


    val room_version = "2.7.2"
//    implementation("io.github.theapache64:rebugger:1.0.1")
    implementation("androidx.room:room-ktx:${room_version}")
// optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:${room_version}")
// optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:${room_version}")
// optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:${room_version}")
// optional - Test helpers
    testImplementation("androidx.room:room-testing:${room_version}")
// optional - Paging 3 Integration
    implementation("androidx.room:room-paging:${room_version}")
    implementation("androidx.room:room-runtime:$room_version")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-navigation3-android")
//    implementation("androidx.navigation3:navigation3-*:1.0.0-alpha08")
//    implementation("androidx.navigation3:navigation3-runtime:1.0.0-alpha08")
//    implementation("androidx.navigation3:navigation3-ui:1.0.0-alpha08")
    implementation(libs.androidx.ui)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.activity:activity-ktx:1.8.0")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(platform("androidx.compose:compose-bom:2025.05.00"))
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.navigation3.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0-rc01")
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
}