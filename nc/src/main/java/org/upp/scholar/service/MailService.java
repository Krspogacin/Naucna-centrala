package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.upp.scholar.security.TokenUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class MailService implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RuntimeService runtimeService;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${mail.alias}")
    private String senderAlias;
    @Value("${frontend.port}")
    private String FRONTEND_PORT;
    @Value("${frontend.address}")
    private String FRONTEND_ADDRESS;
    private static final String HTTP_PREFIX = "http://";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("korisnicko_ime");
        String email = (String) delegateExecution.getVariable("email");
        this.sendRegistrationNotification(username, email);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "korisnik_verifikovan", false);
    }

    @Async
    public void sendRegistrationNotification(final String username, final String email) throws MailException, MessagingException {
        MailService.log.info("Sending email to '{}' with email address: '{}", username, email);

        final MimeMessage mail = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mail, false, "utf-8");
        try {
            helper.setTo(email);
            helper.setFrom(new InternetAddress(this.senderEmail, this.senderAlias));
            helper.setSubject("Naučna centrala - aktiviraj nalog");
            final String message = "Pozdrav " + username + ",<br><br>"
                    + "Da biste aktivirali nalog molim vas: "
                    + "<a href=\"" + this.makeActivationLink(username) + "\">kliknite ovde.</a><br><br>"
                    + "<br>"
                    + "S poštovanjem, <br><br>"
                    + "<i>Naučna centrala</i>";
            helper.setText(message, true);
            this.javaMailSender.send(mail);
        } catch (final MessagingException | UnsupportedEncodingException e) {
            throw new MessagingException();
        }

        MailService.log.info("Email is sent successfully to '{}' with email '{}'", username, email);
    }

    private String makeActivationLink(final String username) {
        return MailService.HTTP_PREFIX.concat(this.FRONTEND_ADDRESS).concat(":").concat(this.FRONTEND_PORT).concat("/verify_account?user=").concat(username);
    }
}
