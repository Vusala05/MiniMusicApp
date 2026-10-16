plugins {
    id("musicapp.android.library")
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.core_data"

    defaultConfig {
        buildConfigField("String", "CLIENT_ID","\"adac2450\"" )
        buildConfigField("String","BASE_URL","\"https://api.jamendo.com/v3.0/\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures{
        buildConfig = true
    }

}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.logging.interceptor)

    //Database
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json.v1110)
    implementation(libs.converter.kotlinx.serialization)




}