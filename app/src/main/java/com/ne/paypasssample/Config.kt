package com.thehomedepot.core.googlepay


import com.google.api.client.util.PemReader
import com.google.api.client.util.SecurityUtils
import org.json.JSONObject
import java.io.Reader
import java.io.StringReader
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec


object Config {



    val SERVICE_ACCOUNT_EMAIL_ADDRESS = "" //TODO Service email goes here
    val SERVICE_ACCOUNT_FILE = "assets/service_account.json"
    val ISSUER_ID = "" //TODO Google Pay Pass Issuer ID goes here
    val API_KEY = ""//TODO API Key goes here

    val ORIGINS = mutableListOf<String>()
    val AUDIENCE = "google"
    val JWT_TYPE ="savetoandroidpay"
    val SCOPES = mutableListOf<String>()

    lateinit var SERVICE_ACCOUNT_PRIVATE_KEY: RSAPrivateKey

    val APPLICATION_NAME = "THDNP"

    init {

        ORIGINS.add("http://www.homedepot.com")
        ORIGINS.add("https://homedepot.com")
        ORIGINS.add("https://www.homedepot.com")

        SCOPES.add("https://www.googleapis.com/auth/wallet_object.issuer")


        try {
            val content = loadJSONFromAsset()
            val privateKeyJson = JSONObject(content)
            val privateKeyPkcs8 = privateKeyJson.get("private_key") as String
            val reader: Reader = StringReader(privateKeyPkcs8)
            val section: PemReader.Section = PemReader.readFirstSectionAndClose(reader, "PRIVATE KEY")
            val bytes: ByteArray = section.base64DecodedBytes
            val keySpec = PKCS8EncodedKeySpec(bytes)
            val keyFactory: KeyFactory = SecurityUtils.getRsaKeyFactory()
            SERVICE_ACCOUNT_PRIVATE_KEY = (keyFactory.generatePrivate(keySpec) as RSAPrivateKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun loadJSONFromAsset(): String {

        val inputStream = javaClass.classLoader?.getResourceAsStream(SERVICE_ACCOUNT_FILE)
        val size = inputStream!!.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)


    }



}

