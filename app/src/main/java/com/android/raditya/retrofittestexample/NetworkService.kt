package com.android.raditya.retrofittestexample

import com.android.raditya.retrofittestexample.model.QuoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkService {
    @GET
    fun quoteOfTheDay(
        @Url string: String = "https://gist.githubusercontent.com/radityagumay/a3e7f8109c79867b954a710685694535/raw/31c98870beaec5554b01045c94d008805c93e97c/quotes.json"
    ): Call<QuoteResponse>
}