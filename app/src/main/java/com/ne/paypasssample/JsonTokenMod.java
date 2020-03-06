package com.ne.paypasssample;

import android.util.Base64;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.oauth.jsontoken.Clock;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.crypto.AsciiStringSigner;
import net.oauth.jsontoken.crypto.Signer;

import java.security.SignatureException;

public class JsonTokenMod extends JsonToken {

    private Signer signer2;
    private String baseString;

    public JsonTokenMod(Signer signer) {
        super(signer);
        this.signer2 = signer;
    }

    public JsonTokenMod(Signer signer, Clock clock) {
        super(signer, clock);
    }

    public JsonTokenMod(JsonObject payload) {
        super(payload);
    }

    public JsonTokenMod(JsonObject payload, Clock clock) {
        super(payload, clock);
    }

    @Override
    public String serializeAndSign() throws SignatureException {

        String baseString = computeSignatureBaseString();
        String sig = getSignature();
        return toDotFormat(baseString, sig);
    }

    private String getSignature() throws SignatureException {

        String signature;
        // now, generate the signature
        AsciiStringSigner asciiSigner = new AsciiStringSigner(signer2);
        signature = Base64.encodeToString(asciiSigner.sign(baseString), Base64.NO_PADDING);

        return signature;
    }

    public static String toDotFormat(String... parts) {
        return Joiner.on('.').useForNull("").join(parts);
    }

    @Override
    protected String computeSignatureBaseString() {
        if (baseString != null && !baseString.isEmpty()) {
            return baseString;
        }

        try {
            baseString = toDotFormat(Base64.encodeToString(toJson(getHeader()).getBytes("UTF-8"), Base64.NO_PADDING),
                    Base64.encodeToString( toJson(getPayloadAsJsonObject()).getBytes("UTF-8"), Base64.NO_PADDING)
            );
        }catch (Exception e){

        }

        return baseString;
    }


    public static String toJson(JsonObject json) {
        return new Gson().toJson(json);
    }
}
