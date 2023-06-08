plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

kotlin {
  jvmToolchain(11)
}

android {

  namespace = "io.github.azizndao.trailer"
  compileSdk = 33

  defaultConfig {
    applicationId = "io.github.azizndao.trailer"
    minSdk = 21
    targetSdk = 33
    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("debug")
    }

    debug {
      applicationIdSuffix = ".debug"
      versionNameSuffix = "-DEBUG"
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
    compose = true
    buildConfig = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get().toString()
  }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(project(":core:model"))
  implementation(project(":core:network"))

  implementation(libs.androidyoutubeplayer.core)

  implementation(libs.glide.compose)

  implementation(libs.kotlinx.collections.immutable)

  implementation(libs.datastore.preferences)

  implementation(libs.timber)

  implementation(libs.androidx.paging.runtime.ktx)
  implementation(libs.androidx.paging.compose)

  implementation(libs.koin.android)
  implementation(libs.koin.compose)

  implementation(libs.navigation.compose)

  implementation(libs.accompanist.placeholder.material)

  implementation(libs.androidx.palette.ktx)

  implementation(libs.androidx.ktx)
  implementation(libs.lifecycle.runtime.compose)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.util)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.testManifest)
}