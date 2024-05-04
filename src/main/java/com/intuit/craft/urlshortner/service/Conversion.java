package com.intuit.craft.urlshortner.service;

public interface Conversion {

    Integer decode(String input);

    String encode(String input);
}
