plugins {
    id("musicapp.android.application")
    id("musicapp.android.compose")
    id("musicapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.example.musicapplication"

    defaultConfig {
        applicationId = "com.example.musicapplication"
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {


    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.logging.interceptor)

    //WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)


    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":navigation"))
    implementation(project(":feature-home:impl"))
    implementation(project(":feature-home:api"))
    implementation(project(":feature-explore:api"))
    implementation(project(":feature-explore:impl"))
    implementation(project(":feature-download:api"))
    implementation(project(":feature-download:impl"))
    implementation(project(":feature-detail:api"))
    implementation(project(":feature-detail:impl"))
    implementation(project(":core-sync"))
    implementation(project(":service"))
}