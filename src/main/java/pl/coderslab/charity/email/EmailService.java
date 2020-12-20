package pl.coderslab.charity.email;

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

    public void sendEmailToChangeForgottenPassword(String email, String token) throws MessagingException {
        MimeMessage mailMessage = mailMessageConfiguration(email, "Oddam w niechciane ręce - reset hasła",
                "<p style = \"font-size: 20px\"> Dzień dobry, </p>" +
                        "<p> w celu zresetowania i ustawienia nowego hasła w serwisie \"Oddam w niechciane " +
                        "ręce\" proszę nacisnąć w poniższy link:</p>"
                        + "<a href ='http://localhost:8080/resetPassword?token=" + token + "'>Reset hasła</a>");
        javaMailSender.send(mailMessage);
    }

    public void sendEmailToActivateNewAccount(String email, String token) throws MessagingException {
        MimeMessage mailMessage = mailMessageConfiguration(email, "Oddam w niechciane ręce - aktywacja konta",
                "<p> Dziękujemy za rejestrację w serwisie \"Oddam w niechciane ręce\".</p>"
        + "<p> Kliknij w poniższy link aby dokończyć rejestrację i zweryfikować swoje konto:</p>"
        + "<a href ='http://localhost:8080/verify?token=" + token + "'>Weryfikacja</a>");
        javaMailSender.send(mailMessage);
    }


    public void sendEmailWithDonationDetails(String email, String donationDetails) throws MessagingException {
        MimeMessage mailMessage = mailMessageConfiguration(email, "Oddam w niechciane ręce - Twoja darowizna",
                "<p> Bardzo dziękujemy za darowiznę. </p>"
                        + "<p> Szczegóły Twojej darowizny:</p>"
                        + donationDetails);
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
