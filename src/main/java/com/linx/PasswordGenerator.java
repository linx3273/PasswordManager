package com.linx;
import java.util.Random;

public class PasswordGenerator {
    // THANK YOU Python3 <3
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    private static final int min = 10;
    private static final int max = 15;

    public String generateRandomString(){
        Random random = new Random();
        int length = random.nextInt(max - min + 1) + min;

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++){
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


}
