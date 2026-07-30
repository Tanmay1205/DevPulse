package com.tanmay.devpulse.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String to, String token) {

        String resetLink = frontendUrl + "/reset-password?token=" + token;

        String subject = "DevPulse - Password Reset Request";

        String body = """
                <html>
                <body>
                    <h2>Reset Your Password</h2>

                    <p>Hello,</p>

                    <p>We received a request to reset your DevPulse account password.</p>

                    <p>
                        <a href="%s">
                            Click here to reset your password
                        </a>
                    </p>

                    <p>This link will expire in 15 minutes.</p>

                    <p>If you didn't request this, you can safely ignore this email.</p>

                    <br>

                    <p>Regards,</p>
                    <p>DevPulse Team</p>

                </body>
                </html>
                """.formatted(resetLink);

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

            logger.info("Password reset email sent to {}", to);

        } catch (MessagingException | MailException e) {

            logger.error("Failed to send email to {}", to, e);

            throw new RuntimeException("Unable to send password reset email");
        }
    }
}