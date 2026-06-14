package com.example.clinicmanagementsystem.registration.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test void hasText_null_returnsFalse()      { assertFalse(StringUtils.hasText(null)); }
    @Test void hasText_empty_returnsFalse()     { assertFalse(StringUtils.hasText("")); }
    @Test void hasText_whitespace_returnsFalse(){ assertFalse(StringUtils.hasText("   ")); }
    @Test void hasText_text_returnsTrue()       { assertTrue(StringUtils.hasText("hi")); }
    @Test void hasText_paddedText_returnsTrue() { assertTrue(StringUtils.hasText("  hi  ")); }

    @Test void isBlank_null_returnsTrue()       { assertTrue(StringUtils.isBlank(null)); }
    @Test void isBlank_empty_returnsTrue()      { assertTrue(StringUtils.isBlank("")); }
    @Test void isBlank_whitespace_returnsTrue() { assertTrue(StringUtils.isBlank("   ")); }
    @Test void isBlank_text_returnsFalse()      { assertFalse(StringUtils.isBlank("hi")); }
    @Test void isBlank_paddedText_returnsFalse(){ assertFalse(StringUtils.isBlank("  hi  ")); }

    @Test void isEmpty_null_returnsTrue()       { assertTrue(StringUtils.isEmpty(null)); }
    @Test void isEmpty_empty_returnsTrue()      { assertTrue(StringUtils.isEmpty("")); }
    @Test void isEmpty_whitespace_returnsTrue() { assertTrue(StringUtils.isEmpty("   ")); }
    @Test void isEmpty_text_returnsFalse()      { assertFalse(StringUtils.isEmpty("hi")); }
}
