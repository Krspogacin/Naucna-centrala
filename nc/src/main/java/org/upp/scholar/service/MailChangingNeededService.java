package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.ScientificWork;
import org.upp.scholar.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class MailChangingNeededService implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${mail.alias}")
    private String senderAlias;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificWork scientificWork = (ScientificWork) delegateExecution.getVariable("scientific_work");
        String editor = (String) delegateExecution.getVariable("editor_who_selects_reviewers");
        User user = (User) delegateExecution.getVariable("user");
        Long hours = (Long) delegateExecution.getVariable("broj_sati_za_izmenu");
        this.sendRegistrationNotification(user.getUsername(), user.getEmail(), editor, scientificWork, hours);
    }

    @Async
    public void sendRegistrationNotification(final String username, final String email, final String editor, final ScientificWork scientificWork, final Long hours) throws MailException, MessagingException {
        log.info("Sending email to '{}' with email address: '{}", username, email);

        final MimeMessage mail = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mail, false, "utf-8");
        try {
            helper.setTo(email);
            helper.setFrom(new InternetAddress(this.senderEmail, this.senderAlias));
            helper.setSubject("Naučna centrala - potrebna korekcija rada");
            final String message = "Pozdrav " + username + ",<br><br>"
                    + "Kreirani naučni rad sa nazivom: "
                    + "<br><br>"
                    + "<h1>" +  scientificWork.getTitle() + "</h1>"
                    + "<br><br>"
                    + "je potrebno korigovati, tražene promene vas čekaju na Naučnoj centrali."
                    + "<br>"
                    + "Rad je poslat na korekciju za koju imate: <i>" + hours + "<i> sati."
                    + "<br><br>"
                    + "S poštovanjem, <br><br>"
                    + "Urednik: <i>" + editor  + "</i>";
            helper.setText(message, true);
            this.javaMailSender.send(mail);
        } catch (final MessagingException | UnsupportedEncodingException e) {
            throw new MessagingException();
        }

        log.info("Email is sent successfully to '{}' with email '{}'", username, email);
    }
}
