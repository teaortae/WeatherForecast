package com.tae.baselibrary.modules

import com.tae.baselibrary.repository.BaseRepository
import org.koin.dsl.module

object BaseRepositoryModule {
    val module = module {
        single { BaseRepository() }
    }
}
