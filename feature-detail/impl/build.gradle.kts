plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.example.feature.detail.impl"
}

dependencies {

    implementation(libs.logging.interceptor)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json.v1110)

    implementation(project(":core-domain"))
    implementation(project(":feature-detail:api"))
    implementation(project(":feature-home:api"))

}