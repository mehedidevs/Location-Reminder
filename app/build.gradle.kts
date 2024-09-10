plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.udacity.project4"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.udacity.project4"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Core and AppCompat Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    
    // Jetpack Components
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    
    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.firebase.auth)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    
    // Koin for Dependency Injection
    implementation(libs.koin.android)
    
    
    // Coroutine support
    implementation(libs.kotlinx.coroutines.android)
    
    
    // Play Services and Firebase
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.auth.ktx)
    // Dependencies for local unit tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("org.robolectric:robolectric:4.9.2")
    testImplementation("com.google.truth:truth:1.1.5")
    testImplementation("org.mockito:mockito-core:5.4.0")
    
    // AndroidX Test - JVM testing
    testImplementation("androidx.test:core-ktx:1.6.1")
    testImplementation("androidx.test.ext:junit-ktx:1.2.1")
    testImplementation("androidx.test:rules:1.6.1")
    
    // AndroidX Test - Instrumented testing
    androidTestImplementation("androidx.test:core-ktx:$1.2.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("org.robolectric:annotations:4.9.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    androidTestImplementation("androidx.test.espresso.idling:idling-concurrent:3.6.1")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:core-ktx:1.6.1")
    // Koin testing tools
    androidTestImplementation("io.insert-koin:koin-test:3.4.3")
    // Needed JUnit version
    androidTestImplementation("io.insert-koin:koin-test-junit4:3.4.3")
    
    // Once https://issuetracker.google.com/127986458 is fixed this can be testImplementation
    debugImplementation("androidx.fragment:fragment-testing:1.1.0-alpha07")
    implementation("androidx.test:core:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.8.3")
    androidTestImplementation("org.mockito:mockito-core:5.4.0")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.3")
    
    
}
