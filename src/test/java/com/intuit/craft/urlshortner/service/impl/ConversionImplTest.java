package com.intuit.craft.urlshortner.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConversionImpl.class})
@ExtendWith(SpringExtension.class)
class ConversionImplTest {
    @Autowired
    private ConversionImpl conversionImpl;

    @Test
    void testEncode() {
        assertEquals("0000003", conversionImpl.encode("https://example.org/example", 3));
    }

    @Test
    void testDecode() {
        assertEquals(125187160, conversionImpl.decode("https://example.org/example").intValue());
        assertEquals(655738317, conversionImpl.decode("Input").intValue());
        assertEquals(250, conversionImpl.decode("42").intValue());
    }

    @Test
    void testBase10ToBase62() {
        assertEquals("0000001", conversionImpl.base10ToBase62(1));
    }

    @Test
    void testConvert() {
        assertEquals(36, conversionImpl.convert('A').intValue());
        assertEquals(0, conversionImpl.convert('0').intValue());
        assertEquals(10, conversionImpl.convert('a').intValue());
        assertEquals(-1, conversionImpl.convert('\u0000').intValue());
        assertEquals(-1, conversionImpl.convert('\\').intValue());
    }

    @Test
    void testBase62ToBase10() {
        assertEquals(125187160, conversionImpl.base62ToBase10("https://example.org/example").intValue());
        assertEquals(250, conversionImpl.base62ToBase10("42").intValue());
    }
}
