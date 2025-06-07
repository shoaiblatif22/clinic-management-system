package com.example.clinicmanagementsystem.user.resetpassword.service;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.user.resetpassword.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.user.resetpassword.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final JavaMailSender mailSender;

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
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password changed successfully for user: {}", user.getEmailAddress());
    }

    @Override
    public void requestPasswordReset(String emailAddress) {
        UserEntity user = userRepository.findByEmailAddress(emailAddress)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + emailAddress));

        String token = UUID.randomUUID().toString();
        createPasswordResetToken(user, token);
        sendPasswordResetEmail(user.getEmailAddress(), token);
        log.info("Password reset token generated and email sent to user: {}", emailAddress);
    }
    
    private void sendPasswordResetEmail(String email, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Password Reset Request");
            mailMessage.setText("To reset your password, click the link below:\n" +
                "http://your-frontend-url/reset-password?token=" + token);
            
            mailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Error sending password reset email", e);
            throw new RuntimeException("Error sending password reset email", e);
        }
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
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
        log.info("Password successfully reset for user: {}", user.getEmailAddress());
    }
}