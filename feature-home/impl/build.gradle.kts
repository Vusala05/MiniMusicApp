plugins {
    id("musicapp.android.library")
    alias(libs.plugins.kotlin.serialization)
    id("musicapp.android.hilt")


}

android {
    namespace = "com.example.feature.home.impl"

}

dependencies {

    implementation(libs.logging.interceptor)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json.v1110)


    implementation(project(":core-data"))
    implementation(project(":feature-home:api"))
}