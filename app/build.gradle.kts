plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.bookapp"
    compileSdk = 34
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.bookapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"${project.findProperty("RAPIDAPI_KEY")}\"")

    }

    buildTypes {
        release {
            // Додаємо захищений API ключ з файлу gradle.properties
            buildConfigField("String", "API_KEY", "\"${project.findProperty("RAPIDAPI_KEY")}\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Material Design Components для UI компонентів, таких як BottomNavigationView, AppBar, TabLayout тощо.

    // RecyclerView для створення списків та сіток
    implementation(libs.androidx.recyclerview)

    // ViewModel та LiveData для MVVM архітектури
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Retrofit для роботи з HTTP-запитами до API
    implementation(libs.retrofit)

    // Конвертер JSON для Retrofit (Gson)
    implementation(libs.converter.gson)

    // OkHttp для логування запитів (зручно для налагодження)
    implementation(libs.logging.interceptor)

    // Coil для завантаження зображень (легкий і ефективний для Android)
    implementation(libs.coil)

    // Kotlin Coroutines для асинхронної роботи з API
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ConstraintLayout для гнучкого розташування елементів
    implementation(libs.androidx.constraintlayout.v214)

    implementation(libs.glide)

}