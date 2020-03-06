package com.ne.paypasssample

import android.util.Log
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.util.Utils
import com.google.api.services.walletobjects.model.LoyaltyObject
import com.thehomedepot.core.googlepay.Config


object SavePassUtil {


    suspend fun savePassToPay(data: JwtRequest): String?{
        val url = "https://walletobjects.googleapis.com/walletobjects/v1/jwt?key=${Config.API_KEY}"


        val saveToPayApi = RetrofitBuilder.create(ApiEndpoints::class.java, "https://walletobjects.googleapis.com/")

        try{
            val response = saveToPayApi.saveLoyaltyObject(url, data)
            if(!response.isSuccessful) {
                log(String(response.errorBody()?.bytes()!!))
            }else{
                return response.body()?.saveUri
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        return null



    }

    suspend fun insertLoyaltyObject(data: LoyaltyObject): LoyaltyObject?{
        val url = "https://walletobjects.googleapis.com/walletobjects/v1/loyaltyObject"

        val cred = authorize()

        val saveToPayApi = RetrofitBuilder.create(ApiEndpoints::class.java, "https://walletobjects.googleapis.com/")

        try{
            val response = saveToPayApi.insertLoyaltyObject(url, "Bearer ${cred?.accessToken?:""}", data)
            if(!response.isSuccessful) {
                log(String(response.errorBody()?.bytes()!!))
            }else{
                return response.body()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        return null



    }

    suspend fun authorize(): GoogleCredential?{
        val credential = GoogleCredential.Builder().setTransport(Utils.getDefaultTransport())
                .setJsonFactory(Utils.getDefaultJsonFactory())
                .setServiceAccountId(Config.SERVICE_ACCOUNT_EMAIL_ADDRESS)
                .setServiceAccountScopes(mutableListOf("https://www.googleapis.com/auth/wallet_object.issuer"))
                .setServiceAccountPrivateKey(Config.SERVICE_ACCOUNT_PRIVATE_KEY)
                .build()

        credential.refreshToken()

        log("Access Token: ${credential.accessToken}")

        //val cred = GoogleCredential.fromStream(THDApplication.getInstance().assets.open(Config.SERVICE_ACCOUNT_FILE)).createScoped(mutableListOf("https://www.googleapis.com/auth/wallet_object.issuer"))
        return credential

    }


    fun log(message: String){
        Log.e("PAY_API", message)
    }

}