plugins {
    id("musicapp.android.library")
    alias(libs.plugins.kotlin.serialization)
    id("musicapp.android.hilt")


}

android {
    namespace = "com.example.navigation"

}

dependencies {
    implementation(libs.logging.interceptor)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core-ui"))
}