plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.core_data"

    defaultConfig {
        buildConfigField("String", "CLIENT_ID","\"adac2450\"" )
        buildConfigField("String","BASE_URL","\"https://api.jamendo.com/v3.0/\"")
    }
    buildFeatures{
        buildConfig = true
    }

}

dependencies {

    //Database
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json.v1110)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.logging.interceptor)





}