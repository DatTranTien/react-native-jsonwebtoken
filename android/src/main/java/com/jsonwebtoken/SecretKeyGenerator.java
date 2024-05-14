package com.jsonwebtoken;

import java.security.SecureRandom;
import java.util.Base64;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.crypto.params.KeyParameter;

public class SecretKeyGenerator {
    public static String generateRandomBase64(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

        public static String generateSecretKey(String password, String salt) throws Exception {
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        byte[] keyBytes = SCrypt.generate(password.getBytes(), saltBytes, 16384, 8, 1, 32);
        return Base64.getEncoder().encodeToString(keyBytes);
    }


    public static String generateIV() {
        return generateRandomBase64(16);
    }
}
