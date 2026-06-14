package com.example.clinicmanagementsystem.password_reset.service;

import com.example.clinicmanagementsystem.password_reset.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.password_reset.events.PasswordResetEvent;
import com.example.clinicmanagementsystem.password_reset.repository.PasswordResetTokenRepository;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    private static final String PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public void createPasswordResetToken(UserEntity user, String token) {
        // Check for existing token for this user
        Optional<PasswordResetToken> existingTokenOpt = tokenRepository.findByUser(user);
        PasswordResetToken resetToken;
        if (existingTokenOpt.isPresent()) {
            resetToken = existingTokenOpt.get();
            resetToken.setToken(token);
            resetToken.setExpiryDate(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))); // 24 hours
        } else {
            resetToken = new PasswordResetToken(token, user);
        }
        tokenRepository.save(resetToken);

        log.info("Created password reset token for user: {}", user.getEmailAddress());
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new IllegalArgumentException("Token has expired");
        }

        return resetToken.getUser().getEmailAddress();
    }

    @Override
    public void changeUserPassword(UserEntity user, String newPassword) {
        if (!pattern.matcher(newPassword).matches()) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, " +
                "contain at least one digit, one lowercase letter, one uppercase letter, " +
                "one special character (@#$%^&+=), and no whitespace");
        }
        userRepository.save(user.toBuilder().password(passwordEncoder.encode(newPassword)).build());
        log.info("Password changed successfully for user: {}", user.getEmailAddress());
    }

    @Override
    @Transactional
    public void requestPasswordReset(String emailAddress) {
        userRepository.findByEmailAddressIgnoreCase(emailAddress)
                .filter(UserEntity::isEnabled)
                .ifPresent(user -> {
                    String token = UUID.randomUUID().toString();
                    createPasswordResetToken(user, token);
                    eventPublisher.publishEvent(new PasswordResetEvent(user, token, null));
                    log.info("Password reset email sent to: {}", emailAddress);
                });
    }



    @Override
    public void resetPassword(String token, String newPassword) {
        // Validate password pattern
        if (!pattern.matcher(newPassword).matches()) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, " +
                "contain at least one digit, one lowercase letter, one uppercase letter, " +
                "one special character (@#$%^&+=), and no whitespace");
        }

        // Validate token and reset password
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token has expired");
        }

        UserEntity user = resetToken.getUser();
        userRepository.save(user.toBuilder().password(passwordEncoder.encode(newPassword)).build());
        tokenRepository.delete(resetToken);
        log.info("Password successfully reset for user: {}", user.getEmailAddress());
    }
}
