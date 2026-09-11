package alpha.util;

import alpha.exception.ApiException;
import java.security.SecureRandom;
import java.util.UUID;

// Created by: Guacamoleboy
// ________________________
// Last updated: 26/03-2026

public class KeyGenerator {

    // Attributes
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int PLACEMENT_CORRECTION = 10;
    private static final String ACCEPTED_VALUES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";

    // _________________________________________________________________________________________________________________

    public static String generate16Key() {
        return generateKey(16);
    }

    // _________________________________________________________________________________________________________________

    public static String generate32Key() {
        return generateKey(32);
    }

    // _________________________________________________________________________________________________________________

    public static String generate64Key() {
        return generateKey(64);
    }

    // _________________________________________________________________________________________________________________

    public static String generate96Key() {
        return generateKey(96);
    }

    // _________________________________________________________________________________________________________________

    public static String generate128Key() {
        return generateKey(128);
    }

    // _________________________________________________________________________________________________________________

    public static String generateKey(int length) {
        if (length <= 0) {
            throw new ApiException(500, "Invalid key length, must be over 0..");
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(ACCEPTED_VALUES.length());
            stringBuilder.append(ACCEPTED_VALUES.charAt(index));
        }

        String finalKey = stringBuilder.toString();
        return finalKey;
    }

    // _________________________________________________________________________________________________________________

    public static int generateNumberKey(int length) {
        if (length <= 0 || length > 9) {
            throw new ApiException(500, "Invalid length applied.. Please only use 1 - 9.");
        }
        int number = 0;
        for (int i = 0; i < length; i++) {
            int digit = secureRandom.nextInt(10);
            number = number * PLACEMENT_CORRECTION + digit;
        }
        return number;
    }

    // _________________________________________________________________________________________________________________

    public static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }

    // _________________________________________________________________________________________________________________

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

}