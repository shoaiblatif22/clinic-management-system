package com.example.clinicmanagementsystem.user.registration.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for string operations with enhanced logging.
 */
@Slf4j
public class StringUtils {
    
    /**
     * Check if a string has text (not null and not blank after trimming).
     * 
     * @param str the string to check
     * @return true if the string has text, false otherwise
     */
    public static boolean hasText(String str) {
        boolean result = str != null && !str.trim().isEmpty();
        log.debug("Checking if string has text: '{}' - Result: {}", str, result);
        return result;
    }
    
    /**
     * Check if a string is null or empty (after trimming).
     * 
     * @param str the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        boolean result = str == null || str.trim().isEmpty();
        log.debug("Checking if string is empty: '{}' - Result: {}", str, result);
        return result;
    }
    
    /**
     * Check if a string is blank (null, empty, or whitespace only).
     * 
     * @param str the string to check
     * @return true if the string is blank, false otherwise
     */
    public static boolean isBlank(String str) {
        boolean result = str == null || str.trim().isEmpty();
        log.debug("Checking if string is blank: '{}' - Result: {}", str, result);
        return result;
    }
}
