package io.fajarca.movies.di.module

import android.app.Application
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.fajarca.movies.BuildConfig
import io.fajarca.movies.util.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpCache(application : Application) : Cache {
        val cacheSize : Long = 10 * 10 * 1024
        return Cache(application.cacheDir , cacheSize)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor : HttpLoggingInterceptor , cache : Cache , timeoutInterceptor : Interceptor) : OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(DEFAULT_CONNECT_TIMEOUT , TimeUnit.MILLISECONDS)
        client.writeTimeout(DEFAULT_WRITE_TIMEOUT , TimeUnit.MILLISECONDS)
        client.readTimeout(DEFAULT_READ_TIMEOUT , TimeUnit.MILLISECONDS)
        client.cache(cache)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(timeoutInterceptor)
        return client.build()
    }


    @Provides
    @Singleton
    fun provideCustomTimeoutInterceptor() : Interceptor {

        val timeoutInterceptor = object : Interceptor {
            override fun intercept(chain : Interceptor.Chain) : Response {
                val request = chain.request()

                var connectTimeout = chain.connectTimeoutMillis()
                var readTimeout = chain.readTimeoutMillis()
                var writeTimeout = chain.writeTimeoutMillis()

                val connectNew = request.header(CONNECT_TIMEOUT)
                val readNew = request.header(READ_TIMEOUT)
                val writeNew = request.header(WRITE_TIMEOUT)

                if (! TextUtils.isEmpty(connectNew)) {
                    connectTimeout = Integer.valueOf(connectNew)
                }
                if (! TextUtils.isEmpty(readNew)) {
                    readTimeout = Integer.valueOf(readNew)
                }
                if (! TextUtils.isEmpty(writeNew)) {
                    writeTimeout = Integer.valueOf(writeNew)
                }

                return chain.withConnectTimeout(connectTimeout , TimeUnit.MILLISECONDS).withReadTimeout(readTimeout , TimeUnit.MILLISECONDS).withWriteTimeout(writeTimeout , TimeUnit.MILLISECONDS).proceed(request)
            }

        }
        return timeoutInterceptor
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }


    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String , okHttpClient : OkHttpClient, gson: Gson) : Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient).build()
    }
}