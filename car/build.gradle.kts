plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.zeroclaw.android.car"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.car.app:app:1.3.0")
    implementation("androidx.core:core-ktx:1.9.0")
}
