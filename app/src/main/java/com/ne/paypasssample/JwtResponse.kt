package com.ne.paypasssample

import com.google.gson.annotations.SerializedName

data class JwtResponse(
        @SerializedName("saveUri")
        var saveUri: String? = null)