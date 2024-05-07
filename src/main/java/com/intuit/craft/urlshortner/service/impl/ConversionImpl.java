package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.service.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.intuit.craft.urlshortner.constants.ServiceConstants.ALLOWED_CHARACTERS;

@Service
@RequiredArgsConstructor
public class ConversionImpl implements Conversion {

    @Override
    public String encode(String input, Integer counter){
        return base10ToBase62(counter);
    }

    @Override
    public Integer decode(String input){
        return base62ToBase10(input);
    }

    /**
     * Converts a base-10 integer to a base-62 string, ensuring that the result is padded to a fixed length for uniformity.
     * This method handles the actual mathematical conversion and character mapping.
     *
     * @param n The integer to convert from base-10 to base-62.
     * @return A string representing the base-62 encoded version of the integer.
     */
    public String base10ToBase62(Integer n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            sb.insert(0, ALLOWED_CHARACTERS.charAt(n % 62));
            n /= 62;
        }
        while (sb.length() != 7) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    /**
     * Converts a character from the base-62 character set (0-9, a-z, A-Z) to its corresponding integer value.
     *
     * @param c The character to convert.
     * @return An integer value corresponding to the base-62 character.
     */
    public Integer convert(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 36;
        }
        return -1;
    }

    /**
     * Converts a base-62 string to a base-10 integer. This function iterates over each character in the string,
     * converting it using {@link #convert(char)} and accumulating the result into a base-10 number.
     *
     * @param s The base-62 string to convert back to a base-10 integer.
     * @return The base-10 integer representation of the base-62 string.
     */
    public Integer base62ToBase10(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            n = n * 62 + convert(s.charAt(i));
        }
        return n;

    }

    
}
