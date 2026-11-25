plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
}

val wimApiUrl = providers.gradleProperty("wimApiUrl").orElse("http://10.0.2.2:8080/api")
val wimLocalDemo = providers.gradleProperty("wimLocalDemo").orElse("false")
val wimConfigUrl = providers.gradleProperty("wimConfigUrl")
    .orElse("https://diegamigo-code.github.io/desarrollo_mobile/config.json")

android {
    namespace = "com.example.desarrollo_mobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.desarrollo_mobile"
        minSdk = 24
        targetSdk = 34
        buildConfigField("String", "WIM_API_URL", "\"${wimApiUrl.get().trimEnd('/')}\"")
        buildConfigField("String", "WIM_CONFIG_URL", "\"${wimConfigUrl.get()}\"")
        buildConfigField("boolean", "WIM_LOCAL_DEMO", wimLocalDemo.get().toBooleanStrict().toString())
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")

    // Room Persistence Library
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Jetpack Navigation & ViewModel
    implementation(libs.navigation.compose)
    implementation(libs.viewmodel.compose)

    // Compose & Core
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
}