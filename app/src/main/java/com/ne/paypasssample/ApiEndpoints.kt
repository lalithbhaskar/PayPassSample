package com.ne.paypasssample

import com.google.api.services.walletobjects.model.LoyaltyObject
import com.ne.paypasssample.JwtRequest
import com.ne.paypasssample.JwtResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiEndpoints {

    @POST
    suspend fun saveLoyaltyObject(@Url url: String, @Body loyaltyObject: JwtRequest): Response<JwtResponse>


    @POST
    suspend fun insertLoyaltyObject(@Url url: String, @Header("Authorization") authorization: String, @Body loyaltyObject: LoyaltyObject): Response<LoyaltyObject>
}