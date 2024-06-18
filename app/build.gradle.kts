plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.8.0"
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
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
        apiVersion = "1.5"
        languageVersion = "1.5"
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.support.annotations)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.places)
    implementation(libs.androidx.benchmark.macro)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //implementation ("com.google.android.gms:play-services-location:21.3.0")
    implementation (libs.androidx.navigation.compose.v240alpha10)
    implementation (libs.play.services.location)
    implementation (libs.kotlinx.coroutines.play.services) // Dependencia para usar `await` con Google Play Services
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Jetpack Compose Navigation
    //implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.androidx.navigation.compose)

    // Hilt Navigation Compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Accompanist for permissions
    implementation(libs.accompanist.permissions)

    // Javapoet
    implementation(libs.javapoet)
    //implementation("com.squareup:javapoet:1.13.0")


    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // OkHttp (necesario para las solicitudes HTTP)
    //implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation(libs.okhttp)

    implementation(libs.play.services.contextmanager)

    //Para el icono
    implementation (libs.coil.compose)
    implementation ("io.coil-kt:coil-compose:2.2.2")

    //para guardar las preferencias de la ciudad seleccionada
    implementation (libs.androidx.preference.ktx)

}

kapt {
    correctErrorTypes = true
}

apply(plugin = "com.google.dagger.hilt.android")
apply(plugin = "dagger.hilt.android.plugin")
