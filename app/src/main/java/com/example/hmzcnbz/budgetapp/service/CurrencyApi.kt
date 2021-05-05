package com.example.hmzcnbz.budgetapp.service

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {
    @GET("latest?access_key=eb0a666126841971b8f333ce7fe09f34&symbols=USD,TRY,GBP")
    suspend fun getCurrency() : Response<JsonObject>
}