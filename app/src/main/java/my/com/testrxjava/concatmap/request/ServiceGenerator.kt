package my.com.testrxjava.concatmap.request

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ServiceGenerator {

    companion object{

        // URL to retrieve post & comments
        private val BASE_URL = "https://jsonplaceholder.typicode.com"

        //create Retrofit instance
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //create implementation of API
        private val requestApi = retrofit.create(RequestApi::class.java)

        //return the API called
        fun getRequestApi(): RequestApi {
            return requestApi
        }

    }

}