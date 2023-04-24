package ru.alex0d.javaspring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "test@example.com";
        String subject = "Test email";
        String text = "This is a test email";

        emailService.sendEmail(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(javaMailSender).send(messageCaptor.capture());

        SimpleMailMessage message = messageCaptor.getValue();
        assertEquals(to, Objects.requireNonNull(message.getTo())[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(text, message.getText());
    }

    @Test
    public void testSendEmailAsync() {
        String to = "test@example.com";
        String subject = "Test email";
        String text = "This is a test email";

        emailService.sendEmail(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(javaMailSender, Mockito.timeout(2000).times(1)).send(messageCaptor.capture());

        SimpleMailMessage message = messageCaptor.getValue();
        assertEquals(to, Objects.requireNonNull(message.getTo())[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(text, message.getText());
    }
}