import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

// Load API properties
val apiProperties = Properties().apply {
    val apiPropertiesFile = rootProject.file("apiurl.properties")
    if (apiPropertiesFile.exists()) {
        load(apiPropertiesFile.inputStream())
    }
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Build config field for BASE_URL
        buildConfigField(
            "String",
            "BASE_URL",
            apiProperties.getProperty("BASE_URL", "\"https://api-v2-b2sit6oh3a-uc.a.run.app/\"")
        )

        // Build config field for SEARCH_BASE_URL
        buildConfigField(
            "String",
            "SEARCH_BASE_URL",
            apiProperties.getProperty(
                "SEARCH_BASE_URL",
                "\"https://mock.apidog.com/m1/735111-711675-default/\""
            )
        )
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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":domain"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Network
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.ok2curl)
    implementation(libs.okhttp)

    // Koin
    implementation(libs.koin.core)

    // Testing
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}