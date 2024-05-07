package com.intuit.craft.urlshortner.service;

public interface Conversion {

    /**
     * Decodes a base-62 string back into a base-10 integer using the base62ToBase10 method.
     * This method is useful for converting a base-62 string (such as a shortened URL identifier) back to its original numeric form.
     *
     * @param input The base-62 string to be decoded.
     * @return The decoded base-10 integer.
     */
    Integer decode(String input);


    /**
     * Encodes a base-10 integer (counter) into a base-62 string using the base10toBase62 method.
     * This method is typically used for generating short and unique alphanumeric identifiers.
     *
     * @param input   The input string for logging or contextual purposes, not used in the actual encoding.
     * @param counter The base-10 integer to be encoded into base-62.
     * @return A base-62 encoded string representing the input integer.
     */
    String encode(String input, Integer counter);
}
