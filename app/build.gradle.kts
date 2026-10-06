plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.musicapplication"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.musicapplication"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.logging.interceptor)


    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":navigation"))
    implementation(project(":feature-home:impl"))
    implementation(project(":feature-home:api"))
    implementation(project(":feature-home:ui"))
    implementation(project(":feature-explore:api"))
    implementation(project(":feature-explore:impl"))
    implementation(project(":feature-explore:ui"))
    implementation(project(":feature-saved:api"))
    implementation(project(":feature-saved:impl"))
    implementation(project(":feature-saved:ui"))
    implementation(project(":feature-detail:api"))
    implementation(project(":feature-detail:impl"))
    implementation(project(":feature-detail:ui"))
    implementation(project(":core-sync"))
    implementation(project(":service"))









}