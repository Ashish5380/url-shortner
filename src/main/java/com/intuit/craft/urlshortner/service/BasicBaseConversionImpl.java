package com.intuit.craft.urlshortner.service;

import java.util.stream.IntStream;


public class BasicBaseConversionImpl {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int base = allowedCharacters.length;

    public String encode(Long input){
        StringBuilder encodedString = new StringBuilder();
        if(input==0){
            return String.valueOf(allowedCharacters[0]);
        }
        while(input > 0){
            encodedString.append(allowedCharacters[(int)(input%base)]);
            input = input / base;
        }
        return encodedString.reverse().toString();
    }

    public Long decode(String input){
        char[] characters = input.toCharArray();
        Integer length = characters.length;

        Long decoded = (long)IntStream.range(0, length)
            .map(i -> allowedString.indexOf(characters[i]) * (int) Math.pow(base, length - i - 1))
            .sum();

        return decoded;
    }

    
}
