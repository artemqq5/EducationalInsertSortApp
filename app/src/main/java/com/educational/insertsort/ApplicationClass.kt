package com.educational.insertsort

import android.app.Application
import com.educational.insertsort.data.repository.RepositoryPracticeImpl
import com.educational.insertsort.data.repository.RepositoryQuestionImpl
import com.educational.insertsort.data.repository.RepositoryTheoryImpl
import com.educational.insertsort.data.storage.Storage
import com.educational.insertsort.data.storage.StorageImpl
import com.educational.insertsort.domain.RepositoryPractice
import com.educational.insertsort.domain.RepositoryQuestion
import com.educational.insertsort.domain.RepositoryTheory
import com.educational.insertsort.domain.ViewModelPractice
import com.educational.insertsort.domain.ViewModelQuestion
import com.educational.insertsort.domain.ViewModelStatistic
import com.educational.insertsort.domain.ViewModelTheory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@ApplicationClass)
            modules(modules = module {
                viewModel {
                    ViewModelTheory(get())
                }

                viewModel {
                    ViewModelPractice(get())
                }

                viewModel {
                    ViewModelQuestion(get())
                }

                viewModel {
                    ViewModelStatistic(get())
                }

                single<RepositoryPractice> {
                    RepositoryPracticeImpl(get())
                }

                single<RepositoryTheory> {
                    RepositoryTheoryImpl(get())
                }

                single<RepositoryQuestion> {
                    RepositoryQuestionImpl(get())
                }

                single<Storage> {
                    StorageImpl(get())
                }
            })
        }

    }
}