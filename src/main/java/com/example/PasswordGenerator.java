package com.example;
import java.security.SecureRandom;


public class PasswordGenerator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Довжина пароля має бути більше 0");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomCharIndex = RANDOM.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomCharIndex));
        }
        return password.toString();
    }

    public static String generateSecurePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Довжина пароля має бти не менше 4 символів");
        }

        StringBuilder password = new StringBuilder(length);

        password.append(CHAR_LOWER.charAt(RANDOM.nextInt(CHAR_LOWER.length())));
        password.append(CHAR_UPPER.charAt(RANDOM.nextInt(CHAR_UPPER.length())));
        password.append(NUMBER.charAt(RANDOM.nextInt(NUMBER.length())));
        password.append(OTHER_CHAR.charAt(RANDOM.nextInt(OTHER_CHAR.length())));

        for (int i = 4; i < length; i++) {
            int randomCharIndex = RANDOM.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomCharIndex));
        }

        return shuffleString(password.toString());
    }

    public static boolean isPasswordStrong(String password) {
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> OTHER_CHAR.indexOf(c) >= 0);

        return password.length() >= 8 && hasLower && hasUpper && hasDigit && hasSpecial;
    }

    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int randomIndex = RANDOM.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        int passwordLength = 12;

        String randomPassword = generatePassword(passwordLength);
        System.out.println("Випадковий пароль: " + randomPassword);

        String securePassword = generateSecurePassword(passwordLength);
        System.out.println("Безпечний пароль: " + securePassword);

        boolean isStrong = isPasswordStrong(securePassword);
        System.out.println("Пароль сильний? " + (isStrong ? "Так" : "Ні"));
    }
}