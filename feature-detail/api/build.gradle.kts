plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.detail.api"

}

dependencies {

    implementation(libs.logging.interceptor)


    implementation(project(":feature-home:api"))
    implementation(project(":feature-download:api"))
    implementation(project(":core-domain"))
    implementation(project(":core-sync"))
    implementation(project(":storage"))
    implementation(project(":service"))



}