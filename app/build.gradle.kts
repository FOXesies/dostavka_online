import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.wayplaner.learn_room"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wayplaner.learn_room"
        minSdk = 24
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
        dataBinding = true
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
    implementation("com.github.a914-gowtham:compose-ratingbar:1.3.4")
    implementation("com.yandex.android:maps.mobile:4.4.0-full")
    implementation("com.google.firebase:firebase-storage:20.2.1")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.android.gms:play-services-safetynet:18.0.1")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("com.google.android.gms:play-services-safetynet:18.0.1")
    implementation(platform("com.google.firebase:firebase-bom:26.5.0"))
    implementation("com.google.firebase:firebase-messaging:23.2.1")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-auth:22.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.7.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    //Retrofit 2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")

    //Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    //OkHttp3
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation ("com.squareup.okhttp3:okhttp-urlconnection:4.4.1")

    //Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    //NAvigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //Foundation
    implementation ("androidx.compose.foundation:foundation:1.6.1")

    //icon
    implementation ("androidx.compose.material:material-icons-extended:1.6.1")

    //Dagger-hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kapt ("com.google.dagger:hilt-android-compiler:2.50")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0-rc01")
    kapt ("androidx.hilt:hilt-compiler:1.2.0-rc01")

    implementation ("com.google.accompanist:accompanist-navigation-animation:0.26.1-alpha")

    //ToogleButtons
    implementation ("com.google.android.material:material:1.11.0")
    implementation("com.robertlevonyan.compose:buttontogglegroup:1.1.2")

    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.datastore:datastore:1.0.0")

    // Coil
    implementation ("io.coil-kt:coil-compose:2.1.0")

    //One Signal
    implementation("com.onesignal:OneSignal:[4.0.0, 4.99.99]")


    //LiveData
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.2")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui:1.6.2")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    apply(plugin = "com.google.gms.google-services")
}