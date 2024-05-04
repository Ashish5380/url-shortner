package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.service.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.intuit.craft.urlshortner.constants.ServiceConstants.ALLOWED_CHARACTERS;

@Service
@RequiredArgsConstructor
public class ConversionImpl implements Conversion {
    static Integer COUNTER = 1000000000;

    @Override
    public String encode(String input){
        String shortUrl = base10ToBase62(COUNTER);
        COUNTER++;
        return shortUrl;
    }

    @Override
    public Integer decode(String input){
        input = input.substring("http://tiny.url/".length());
        return base62ToBase10(input);
    }

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

    public Integer base62ToBase10(String s) {
        Integer n = 0;
        for (int i = 0; i < s.length(); i++) {
            n = n * 62 + convert(s.charAt(i));
        }
        return n;

    }

    
}
