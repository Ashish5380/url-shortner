package com.intuit.craft.urlshortner.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UrlUtilTest {
    @Test
    void testIsUrlValid() {
        assertTrue(UrlUtil.isUrlValid("https://example.org/example"));
        assertFalse(UrlUtil.isUrlValid("^[a-zA-Z0-9$\\-_.+!*'(),]*$"));
    }

    @Test
    void testIsValidCustomSuffix() {
        assertFalse(UrlUtil.isValidCustomSuffix("https://example.org/example"));
        assertTrue(UrlUtil.isValidCustomSuffix("foo"));
        assertTrue(UrlUtil.isValidCustomSuffix("U"));
        assertTrue(UrlUtil.isValidCustomSuffix("Suffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("42"));
        assertTrue(UrlUtil.isValidCustomSuffix(""));
        assertTrue(UrlUtil.isValidCustomSuffix("UU"));
        assertTrue(UrlUtil.isValidCustomSuffix("USuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("U42"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixU"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixSuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("Suffix42"));
        assertTrue(UrlUtil.isValidCustomSuffix("42U"));
        assertTrue(UrlUtil.isValidCustomSuffix("42Suffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("4242"));
        assertTrue(UrlUtil.isValidCustomSuffix("UUU"));
        assertTrue(UrlUtil.isValidCustomSuffix("UUSuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("UU42"));
        assertTrue(UrlUtil.isValidCustomSuffix("USuffixU"));
        assertTrue(UrlUtil.isValidCustomSuffix("USuffixSuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("USuffix42"));
        assertTrue(UrlUtil.isValidCustomSuffix("U42U"));
        assertTrue(UrlUtil.isValidCustomSuffix("U42Suffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("U4242"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixUU"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixUSuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixU42"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixSuffixU"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixSuffixSuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("SuffixSuffix42"));
        assertTrue(UrlUtil.isValidCustomSuffix("Suffix42U"));
        assertTrue(UrlUtil.isValidCustomSuffix("Suffix42Suffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("Suffix4242"));
        assertTrue(UrlUtil.isValidCustomSuffix("42UU"));
        assertTrue(UrlUtil.isValidCustomSuffix("42USuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("42U42"));
        assertTrue(UrlUtil.isValidCustomSuffix("42SuffixU"));
        assertTrue(UrlUtil.isValidCustomSuffix("42SuffixSuffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("42Suffix42"));
        assertTrue(UrlUtil.isValidCustomSuffix("4242U"));
        assertTrue(UrlUtil.isValidCustomSuffix("4242Suffix"));
        assertTrue(UrlUtil.isValidCustomSuffix("424242"));
    }
}
