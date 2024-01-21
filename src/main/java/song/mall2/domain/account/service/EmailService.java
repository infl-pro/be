package song.mall2.domain.account.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendMail(String to, String subject, String text) throws MessagingException {
        MimeMessage mime = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mime, true);

        String from = String.format("%s <%s>", "이메일 인증", "dkclasltmf@naver.com");
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);
        emailSender.send(mime);
    }
}
