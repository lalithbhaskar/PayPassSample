package com.ne.paypasssample

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { saveToGPay() }
    }


    fun saveToGPay(){
        GlobalScope.launch {
            val jwt = Jwt()
            jwt.addLoyaltyClass(Gson().toJsonTree(ResourceDefinition.makeLoyaltyClassResource()))
            jwt.addLoyaltyObject(Gson().toJsonTree(ResourceDefinition.makeLoyaltyObjectResource()))
            val jwtString = jwt.generateSignedJwt()

            SavePassUtil.log("START")
            val uri = SavePassUtil.savePassToPay(JwtRequest(jwtString))
            SavePassUtil.log("END")

            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(uri)
            startActivity(i)
        }
    }


}
