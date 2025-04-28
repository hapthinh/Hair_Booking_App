plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.barbershopapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.barbershopapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.functions.ktx)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.compose.material3:material3:1.2.1")
    implementation ("androidx.compose.ui:ui:1.6.8")

    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    implementation("com.google.firebase:firebase-appcheck-playintegrity")
    implementation ("com.github.a914-gowtham:compose-ratingbar:1.3.12")
    implementation ("com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.compose.ui:ui:1.3.0")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.3.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.3.0")
    implementation ("com.google.firebase:firebase-functions:20.0.2")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

}
