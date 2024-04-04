package org.ait.project.guideline.example.shared.utils.response;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * @author febrihasan
 */
@Slf4j
public class JwtUtils {

    public static void main(String[] args) {
        log.info("secret: {}", createNewSecretKey());
    }

    /**
     * Generates a new secret key.
     * @return The generated secret key as a hexadecimal string.
     */

    private static String createNewSecretKey() {
        byte[] key = new byte[64];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(key);

        // Print the key as a hexadecimal string
        StringBuilder keyString = new StringBuilder();

        for
        (byte b : key) {
            keyString.append(String.format("%02x",b));
        }
        return keyString.toString();
    }
}
