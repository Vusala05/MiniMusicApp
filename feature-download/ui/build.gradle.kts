plugins {
    id("musicapp.android.library")
    id("musicapp.android.compose")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.feature.saved.ui"
}

dependencies {

    implementation(libs.logging.interceptor)

    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":feature-download:api"))
    implementation(project(":feature-home:api"))
    implementation(project(":service"))
    implementation(project(":navigation"))


}