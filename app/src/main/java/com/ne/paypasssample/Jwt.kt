package com.ne.paypasssample

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.thehomedepot.core.googlepay.Config
import net.oauth.jsontoken.JsonToken
import net.oauth.jsontoken.crypto.RsaSHA256Signer
import org.joda.time.Instant
import java.security.InvalidKeyException
import java.security.SignatureException
import java.util.*


class Jwt {

    private var audience: String = Config.AUDIENCE
    private var type: String = Config.JWT_TYPE
    private var iss: String = Config.SERVICE_ACCOUNT_EMAIL_ADDRESS
    private var origins: List<String> = Config.ORIGINS
    private var iat: Instant = Instant(Calendar.getInstance().timeInMillis)

    private var payload: JsonObject = JsonObject()

    private lateinit var signer: RsaSHA256Signer

    init {

        try {
            signer = RsaSHA256Signer(iss,
                    null, Config.SERVICE_ACCOUNT_PRIVATE_KEY)
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }
    }


    fun addLoyaltyClass(resourcePayload: JsonElement?) {
        if (payload["loyaltyClasses"] == null) {
            val loyaltyObjects = JsonArray()
            payload.add("loyaltyClasses", loyaltyObjects)
        }
        val newPayload = payload["loyaltyClasses"] as JsonArray
        newPayload.add(resourcePayload)
        payload.add("loyaltyClasses", newPayload)
        return
    }

    fun addLoyaltyObject(resourcePayload: JsonElement?) {
        if (payload["loyaltyObjects"] == null) {
            val loyaltyObjects = JsonArray()
            payload.add("loyaltyObjects", loyaltyObjects)
        }
        val newPayload = payload["loyaltyObjects"] as JsonArray
        newPayload.add(resourcePayload)
        payload.add("loyaltyObjects", newPayload)
        return
    }

    fun generateUnsignedJwt(): JsonToken {

        val token = JsonTokenMod(signer)
        token.audience = audience
        token.setParam("typ", type)
        token.issuedAt = iat
        val gson = Gson()
        token.payloadAsJsonObject.add("payload", payload)
        token.payloadAsJsonObject.add("origins", gson.toJsonTree(origins))
        return token
    }


    fun generateSignedJwt(): String? {

        val jwtToSign: JsonToken = this.generateUnsignedJwt()
        var signedJwt: String? = null
        try {
            signedJwt = jwtToSign.serializeAndSign()
            //signedJwt = signedJwt?.replace("\n","")
        } catch (e: SignatureException) {
            e.printStackTrace()
        }
        return signedJwt
    }

}