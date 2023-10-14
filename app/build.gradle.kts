plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.gochoa.wikidex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gochoa.wikidex"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    kotlin {
        jvmToolchain(8)
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Material 3
    implementation("com.google.android.material:material:1.10.0")
    //DaggerHilt
    val daggerVersion = "2.48"
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    ksp("com.google.dagger:hilt-compiler:$daggerVersion")

    //Room
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //NavComponent
    val navVersion = "2.7.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    //Fused
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

    //Retrofit y Gson
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    //OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")

}