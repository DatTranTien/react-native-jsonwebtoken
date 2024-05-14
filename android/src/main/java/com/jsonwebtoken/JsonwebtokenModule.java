package com.jsonwebtoken;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.module.annotations.ReactModule;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;

import javax.crypto.Cipher; 
import javax.crypto.spec.SecretKeySpec; 
import javax.crypto.spec.IvParameterSpec; 
import java.security.KeyFactory; 
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64; 
import org.json.JSONObject;
import java.security.interfaces.RSAPublicKey;




@ReactModule(name = JsonwebtokenModule.NAME)
public class JsonwebtokenModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Jsonwebtoken";

  public JsonwebtokenModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private RSAPublicKey parsePublicKey(String publicKeyPEM) throws Exception {
    byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return (RSAPublicKey) kf.generatePublic(spec);
}

  @ReactMethod
    public void verifyToken(String token, String publicKeyPEM, Promise promise) {
    try {
        RSAPublicKey publicKey = parsePublicKey(publicKeyPEM);
        JWSObject jwsObject = JWSObject.parse(token);
        RSASSAVerifier verifier = new RSASSAVerifier(publicKey);

        if (jwsObject.verify(verifier)) {
            promise.resolve(true);
        } else {
           promise.reject("TOKEN_INVALID", "Invalid token: Signature verification failed.");
        }
    } catch (Exception e) {
        promise.reject("ERROR", "Failed to verify token: " + e.getMessage());
        e.printStackTrace();
    }
}

  @ReactMethod
    public void generateSecretKey(Promise promise) {
        try {
            String password = SecretKeyGenerator.generateRandomBase64(20);
            String salt = SecretKeyGenerator.generateRandomBase64(16);
            String key = SecretKeyGenerator.generateSecretKey(password, salt);
            String iv = SecretKeyGenerator.generateIV();

            WritableMap result = Arguments.createMap();
            result.putString("key", key);
            result.putString("iv", iv);

            promise.resolve(result);
        } catch (Exception e) {
            promise.reject("ERROR", "Failed to generate secret key: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void encryptData(String data, String publicKeyPEM, Promise promise) {
        try {
            RSAPublicKey publicKey = parsePublicKey(publicKeyPEM);

            // RSA Encryption of Secret Key
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            WritableMap secretKeyMap = generateSecretKeySync(); // Synchronous internal call to generate secret key
            String secretKeyJSON = new JSONObject(secretKeyMap.toHashMap()).toString();
            byte[] encryptedKey = rsaCipher.doFinal(secretKeyJSON.getBytes("UTF-8"));
            String encryptedKeyBase64 = Base64.getEncoder().encodeToString(encryptedKey);

            // AES Encryption of Data
            String algorithm = "AES/CBC/PKCS5Padding";
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyMap.getString("key")), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(secretKeyMap.getString("iv")));
            Cipher aesCipher = Cipher.getInstance(algorithm);
            aesCipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptedData = aesCipher.doFinal(data.getBytes("UTF-8"));
            String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData);

            // Concatenate encrypted key and data
            String result = encryptedKeyBase64 + "." + encryptedDataBase64;
            promise.resolve(result);
        } catch (Exception e) {
            promise.reject("ERROR", "Encryption failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private WritableMap generateSecretKeySync() throws Exception {
    try {
        String password = SecretKeyGenerator.generateRandomBase64(20);
        String salt = SecretKeyGenerator.generateRandomBase64(16);
        String key = SecretKeyGenerator.generateSecretKey(password, salt);
        String iv = SecretKeyGenerator.generateIV();

        WritableMap result = Arguments.createMap();
        result.putString("key", key);
        result.putString("iv", iv);

        return result;
    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Failed to generate secret key synchronously: " + e.getMessage());
    }
}
}
