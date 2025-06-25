plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.wz.multipead"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.photorecovery.filerecovery.restorefile"
        minSdk = 24
        targetSdk = 35
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
            signingConfig = signingConfigs.getByName("debug")
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
        viewBinding = true
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
    implementation(project(":mobile_ads"))

//    implementation ("com.applovin.mediation:bytedance-adapter:7.2.0.5.0")
//    implementation ("com.applovin.mediation:mintegral-adapter:16.9.71.0")
//    implementation ("com.applovin.mediation:google-adapter:23.6.0.1")
//    implementation ("com.applovin.mediation:vungle-adapter:7.5.0.0")
//    implementation ("com.applovin.mediation:facebook-adapter:6.20.0.0")

//
//    implementation  ("com.google.ads.mediation:applovin:13.3.1.0")
//    implementation  ("com.google.ads.mediation:vungle:7.5.0.0")
//    implementation  ("com.google.ads.mediation:facebook:6.20.0.0")
//    implementation  ("com.google.ads.mediation:mintegral:16.9.71.0")
//    implementation  ("com.google.ads.mediation:pangle:7.2.0.6.0")




}