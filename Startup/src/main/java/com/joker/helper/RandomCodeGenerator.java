package com.joker.helperClasses;

import java.util.Random;

public class RandomCodeGenerator {
    private final static int CODE_LENGTH = 6;
    private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomCode(){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < CODE_LENGTH; i++){
            int randomIndex = (int) (rand.nextFloat() * alphabet.length());
            sb.append(alphabet.charAt(randomIndex));
        }
        return sb.toString();
    }
}
