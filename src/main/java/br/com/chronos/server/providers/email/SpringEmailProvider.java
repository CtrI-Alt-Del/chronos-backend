package br.com.chronos.server.providers.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import br.com.chronos.core.global.domain.exceptions.AppException;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;
import jakarta.mail.MessagingException;

@Component
public class SpringEmailProvider implements EmailProvider {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String senderEmail;

  @Override
  public void sendAuthenticationEmail(Email recipientEmail, Text otpCode) {
    var message = mailSender.createMimeMessage();
    try {
      var template = loadAuthenticationEmailTemplate();
      template = template.replace("#{{otpCode}}", otpCode.value());

      var helper = new MimeMessageHelper(message);
      helper.setFrom(senderEmail);
      helper.setTo(recipientEmail.value());
      helper.setSubject("Pedido de autenticação");
      helper.setText(template, true);
      mailSender.send(message);
    } catch (MessagingException | IOException e) {
      throw new AppException("Email provider exception", e.getMessage());
    }
  }

  private String loadAuthenticationEmailTemplate() throws IOException {
    var resource = new ClassPathResource("ui/templates/authentication-email.html");
    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
  }
}
