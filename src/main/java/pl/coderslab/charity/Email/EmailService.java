package pl.coderslab.charity.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class EmailService {

    private final JavaMailSenderImpl javaMailSender;

    public EmailService(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail() throws MessagingException {
        MimeMessage mailMessage = mailMessageConfiguration("l.kuczynski95@gmail.com", "testSubject", "tesing " +
                "the ablility to send emails");
        javaMailSender.send(mailMessage);
    }

    public void sendEmailToActivateNewAccount(String email, String token) throws MessagingException {
        MimeMessage mailMessage = mailMessageConfiguration(email, "Oddam w niechciane ręce - aktywacja konta",
                "<p> Dziękujemy za rejestrację w serwisie \"Oddam w niechciane ręce\".</p>"
        + "<p> Kliknij w poniższy link aby dokończyć rejestrację i zweryfikować swoje konto:</p>"
        + "<a href ='http://localhost:8080/verify?token=" + token + "'>Weryfikacja</a>");
        javaMailSender.send(mailMessage);
    }

    public MimeMessage mailMessageConfiguration(String recipientsEmail, String subject, String text) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
        helper.setFrom("learningjava.noreply@gmail.com");
        helper.setTo(recipientsEmail);
        helper.setSubject(subject);
        helper.setText(text, true);
        return mailMessage;
    }


}
