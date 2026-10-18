plugins {
    id("musicapp.android.library")
    id("musicapp.android.hilt")

}

android {
    namespace = "com.example.core_sync"

}

dependencies {

    //WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    //Database
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)

    //Network
    implementation(libs.logging.interceptor)

    implementation(project(":core-ui"))
    implementation(project(":storage"))


}