package Retrofit2

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object{
        private var retrofit:Retrofit? = null
        fun  getClient(baseurl:String):Retrofit{
            var builder:OkHttpClient = OkHttpClient.Builder()
                .readTimeout(5000,TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                .connectTimeout(10000,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build()
            var gson:Gson = GsonBuilder().setLenient().create()
              retrofit = Retrofit.Builder()
                 .baseUrl(baseurl)
                 .client(builder)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build()
            return retrofit!!
        }
    }
}