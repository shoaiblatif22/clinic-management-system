package com.example.unittests.UnitTestRegistration;

import com.example.clinicmanagementsystem.user.registration.utils.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class StringUtilsUnitTests {
    @Mock
    private Logger logger;

    @Test
    @DisplayName("hasText should return false for null input")
    public void hasText_ShouldReturnFalse_WhenInputIsNull() {
        assertFalse(StringUtils.hasText(null));
    }

    @Test
    @DisplayName("hasText should return false for empty string")
    public void hasText_ShouldReturnFalse_WhenInputIsEmpty() {
        assertFalse(StringUtils.hasText(""));
    }

    @Test
    @DisplayName("hasText should return false for whitespace only")
    public void hasText_ShouldReturnFalse_WhenInputIsWhitespace() {
        assertFalse(StringUtils.hasText("   "));
    }

    @Test
    @DisplayName("hasText should return true for string with content")
    public void hasText_ShouldReturnTrue_WhenInputHasContent() {
        assertTrue(StringUtils.hasText("test"));
    }

    @Test
    @DisplayName("hasText should return true for string with content and whitespace")
    public void hasText_ShouldReturnTrue_WhenInputHasContentWithWhitespace() {
        assertTrue(StringUtils.hasText("  test  "));
    }

    @Test
    @DisplayName("isEmpty should return true for null input")
    public void isEmpty_ShouldReturnTrue_WhenInputIsNull() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    @DisplayName("isEmpty should return true for empty string")
    public void isEmpty_ShouldReturnTrue_WhenInputIsEmpty() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    @DisplayName("isEmpty should return true for whitespace only")
    public void isEmpty_ShouldReturnTrue_WhenInputIsWhitespace() {
        assertTrue(StringUtils.isEmpty("   "));
    }

    @Test
    @DisplayName("isEmpty should return false for string with content")
    public void isEmpty_ShouldReturnFalse_WhenInputHasContent() {
        assertFalse(StringUtils.isEmpty("test"));
    }

    @Test
    @DisplayName("isBlank should return true for null input")
    public void isBlank_ShouldReturnTrue_WhenInputIsNull() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    @DisplayName("isBlank should return true for empty string")
    public void isBlank_ShouldReturnTrue_WhenInputIsEmpty() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    @DisplayName("isBlank should return true for whitespace only")
    public void isBlank_ShouldReturnTrue_WhenInputIsWhitespace() {
        assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    @DisplayName("isBlank should return false for string with content")
    public void isBlank_ShouldReturnFalse_WhenInputHasContent() {
        assertFalse(StringUtils.isBlank("test"));
    }

    @Test
    @DisplayName("isBlank should return false for string with content and whitespace")
    public void isBlank_ShouldReturnFalse_WhenInputHasContentWithWhitespace() {
        assertFalse(StringUtils.isBlank("  test  "));
    }

}
