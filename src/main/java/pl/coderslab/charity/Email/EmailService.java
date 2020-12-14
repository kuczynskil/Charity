package pl.coderslab.charity.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

@Controller
public class EmailService {

    private final JavaMailSenderImpl javaMailSender;

    public EmailService(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail() {
        SimpleMailMessage mailMessage = mailMessageConfiguration("l.kuczynski95@gmail.com", "testSubject", "tesing " +
                "the ablility to send emails");
        javaMailSender.send(mailMessage);
    }

    public void sendEmailToActivateNewAccount(String email, String token) {
        SimpleMailMessage mailMessage = mailMessageConfiguration(email, "Oddam w niechciane ręce - aktywacja konta",
                "<p> Dziękujemy za rejestrację w serwisie 'Oddam w niechciane ręce'</p>"
        + "<p> Kliknij poniższy link aby dokończyć rejestrację i zweryfikować swoje konto</p>"
        + "<a href ='http://localhost:8080/verify?token" + token + "'>Weryfikacja</a>");
        javaMailSender.send(mailMessage);
    }

    public SimpleMailMessage mailMessageConfiguration(String recipientsEmail, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("learningjava.noreply@gmail.com");
        mailMessage.setTo(recipientsEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        return mailMessage;
    }


}
