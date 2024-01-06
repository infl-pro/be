package song.mall2.domain.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dkclasltmf@naver.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        log.info("[send email] to = {}, message = {}", message.getTo(), message.getText());
        emailSender.send(message);
    }
}
