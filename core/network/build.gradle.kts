@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

secrets {
  defaultPropertiesFileName = "secrets.defaults.properties"
}

kotlin {
  jvmToolchain(11)
}

android {

  buildFeatures {
    buildConfig = true
  }

  namespace = "io.github.azizndao.network"
  compileSdk = 33

  defaultConfig {
    minSdk = 21

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
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
  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx)
  implementation(libs.kotlinx.serialization)
  implementation(libs.koin.android)

  implementation(project( ":core:model"))
}