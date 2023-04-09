package com.fangga.core.di

import androidx.room.Room
import com.fangga.core.BuildConfig
import com.fangga.core.data.UserRepository
import com.fangga.core.data.datastore.UserDataStore
import com.fangga.core.data.source.local.LocalDataSource
import com.fangga.core.data.source.local.room.UserDatabase
import com.fangga.core.data.source.remote.RemoteDataSource
import com.fangga.core.data.source.remote.network.ApiService
import com.fangga.core.domain.repository.IUserRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val API_KEY = BuildConfig.API_KEY
private const val BASE_URL = BuildConfig.BASE_URL

val databaseModule = module {
    factory {
        get<UserDatabase>().userDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, "User.db"
        ).fallbackToDestructiveMigration().build()
    }
    single {

    }
}

val datastoreModule = module {
    single { UserDataStore(androidContext()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", API_KEY)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single<IUserRepository> {
        UserRepository(
            get(),
            get(),
        )
    }
}