package com.android.raditya.retrofittestexample.model

import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("contents") val contents: Contents,
    @SerializedName("success") val success: Success
) {
    data class Contents(
        @SerializedName("quotes") val quotes: List<QuotesItem>?
    ) {
        data class QuotesItem(
            @SerializedName("quote") val quote: String = "",
            @SerializedName("author") val author: String = "",
            @SerializedName("length") val length: String = "",
            @SerializedName("id") val id: String = "",
            @SerializedName("category") val category: String = "",
            @SerializedName("tags") val tags: List<String>?)
    }

    data class Success(@SerializedName("total") val total: Int = 0)
}

