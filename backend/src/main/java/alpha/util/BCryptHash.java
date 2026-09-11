package alpha.util;

import org.mindrot.jbcrypt.BCrypt;

// Created by: Guacamoleboy
// ________________________
// Last updated: 28/02-2026
// By: Guacamoleboy

public class BCryptHash {

    // _________________________________________________________________________________________________________________
    // Hash explained:
    // _______________
    //
    // $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36l6fU6.PFeXgSpFM50.H5e
    //
    // $2a = Alorithm / Version
    // 10$ = Work Factor
    // EixZaYVK1fsbw1ZfbX3OXe = salt (16 bytes)
    // PaWxn96p36l6fU6.PFeXgSpFM50.H5e = hashed String input
    // _________________________________________________________________________________________________________________

    // _________________________________________________________________________________________________________________
    // Expected Handle:
    // ________________
    //
    // Input: jonas68@live.dk
    // Output: $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36l6fU6.PFeXgSpFM50.H5e
    //
    // Limited to bytes or people could spam 72 emojis and get 4 x 72 -> Bytes. Which is odd.
    public static String hash(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input can't be empty..");
        }
        byte[] bytes = input.getBytes();
        if (bytes.length > 72) {
            throw new IllegalArgumentException("Input is too long.. (72 bytes limit)");
        }
        return BCrypt.hashpw(input, BCrypt.gensalt());
    }

    // _________________________________________________________________________________________________________________

    // _________________________________________________________________________________________________________________
    // Expected Handle:
    // ________________
    //
    // Input:
    //      - input = "password123"
    //      - hashed = "$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36l6fU6.PFeXgSpFM50.H5e"
    // Output:
    //      - true
    //      - false
    public static boolean check(String input, String hashed) {
        if (input == null || hashed == null) {
            return false;
        }
        return BCrypt.checkpw(input, hashed);
    }

}