package io.github.azizndao.trailer.utils

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UtilsModule = module {
  singleOf(::UserPreferences)
}