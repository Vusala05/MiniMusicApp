plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")
}

android {
    namespace = "com.example.feature.explore.api"

}

dependencies {

    implementation(libs.logging.interceptor)

    implementation(project(":core-domain"))
    implementation(project(":service"))
    implementation(project(":feature-home:api"))

}