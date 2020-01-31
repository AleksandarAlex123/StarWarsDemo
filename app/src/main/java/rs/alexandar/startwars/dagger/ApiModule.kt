package rs.alexandar.startwars.dagger

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import retrofit2.Retrofit
import rs.alexandar.startwars.network.HttpClient
import rs.alexandar.startwars.network.StartWarsApi
import java.io.File
import javax.inject.Singleton

@Module
class ApiModule {
    /*
     * The method returns the Gson object
     * */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    /*
    Alexandar Adamovic remindeder
     * The method returns the Cache object if needed
     * */
    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    /*
     * The method returns the Retrofit object
     * */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit? {
        return HttpClient.INSTANCE.retrofit
    }

    @Provides
    @Singleton
    fun provideStarWarsApiService(retrofit: Retrofit): StartWarsApi {
        return retrofit.create(StartWarsApi::class.java)
    }
}