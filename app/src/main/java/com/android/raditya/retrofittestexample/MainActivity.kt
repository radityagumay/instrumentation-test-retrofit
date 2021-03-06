package com.android.raditya.retrofittestexample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.raditya.retrofittestexample.R.id
import com.android.raditya.retrofittestexample.R.layout
import com.android.raditya.retrofittestexample.model.QuoteResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity() {
    private var textViewQuoteOfTheDay: TextView? = null
    private var buttonRetry: Button? = null

    private val loggingInterceptor by lazy(NONE) {
        HttpLoggingInterceptor().apply {
            setLevel(Level.BODY)
        }
    }

    private val service by lazy(NONE) {
        Builder()
            .baseUrl(QuoteOfTheDayConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build().create(NetworkService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        textViewQuoteOfTheDay = findViewById<View>(id.text_view_quote) as TextView
        buttonRetry = findViewById<View>(id.button_retry) as Button
        buttonRetry!!.setOnClickListener { quoteOfTheDay() }

        service
        quoteOfTheDay()
    }

    private fun quoteOfTheDay() {
        val call = service.quoteOfTheDay()
        call.enqueue(object : Callback<QuoteResponse> {
            override fun onResponse(call: Call<QuoteResponse>, response: Response<QuoteResponse>) {
                if (response.isSuccessful) {
                    textViewQuoteOfTheDay!!.text = response.body()!!.contents.quotes!![0].quote
                }
            }

            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                //todo
            }
        })
    }
}