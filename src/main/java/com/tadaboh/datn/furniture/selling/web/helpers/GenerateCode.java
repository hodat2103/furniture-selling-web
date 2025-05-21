package com.tadaboh.datn.furniture.selling.web.helpers;

import java.text.Normalizer;
import java.util.Random;

public class GenerateCode {
    public static String generateProductCode() {
        Random random = new Random();
        // Tạo 3 chữ cái ngẫu nhiên
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + random.nextInt(26)); // Chọn ngẫu nhiên từ A-Z
            letters.append(letter);
        }
        //  4 chữ số ngẫu nhiên
        int digits = 1000 + random.nextInt(9000);
        return letters + String.valueOf(digits);
    }

    public static String generateSlug(String name) {
        // Normalize the string and remove diacritical marks (accents)
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");  // Remove accents
        String slug = normalized.toLowerCase()
                .trim() // Remove leading and trailing spaces
                .replaceAll("\\s+", "-") // Replace multiple spaces with a single hyphen
                .replaceAll("[^a-z0-9-]", ""); // Remove non-alphanumeric characters except hyphens
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public static String generateOrderCode() {
        Random random = new Random();
        // Tạo 7 chữ số ngẫu nhiên
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int digit = random.nextInt(10); // Chọn ngẫu nhiên từ 0-9
            digits.append(digit);
        }
        // Tạo 7 ký tự ngẫu nhiên (chữ cái và số)
        StringBuilder lettersAndDigits = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            char character;
            if (random.nextBoolean()) {
                character = (char) ('A' + random.nextInt(26)); // Chọn ngẫu nhiên từ A-Z
            } else {
                character = (char) ('0' + random.nextInt(10)); // Chọn ngẫu nhiên từ 0-9
            }
            lettersAndDigits.append(character);
        }
        return digits.toString() + lettersAndDigits.toString();
    }
    public static String generateShipmentCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder("FMXVN0");
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        code.append('B');
        return code.toString();

    }
    public static String generatePromotionCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                code.append((char) ('A' + random.nextInt(26))); // Chọn ngẫu nhiên từ A-Z
            } else {
                code.append(random.nextInt(10)); // Chọn ngẫu nhiên từ 0-9
            }
        }
        return code.toString();
    }
    public static String generateTrackingNumber() {
        Random random = new Random();
        StringBuilder trackingNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int digit = random.nextInt(10);
            trackingNumber.append(digit);
        }
        return trackingNumber.toString();
    }
}
