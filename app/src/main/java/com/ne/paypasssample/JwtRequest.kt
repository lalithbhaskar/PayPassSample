package com.ne.paypasssample

import com.google.gson.annotations.SerializedName

data class JwtRequest(
        @SerializedName("jwt")
        var jwt: String? = null)