package br.com.chronos.server.providers.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;

import br.com.chronos.core.global.domain.exceptions.AppException;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Email;
import br.com.chronos.core.global.domain.records.Text;
import br.com.chronos.core.notification.interfaces.EmailProvider;

@Component
public class SpringEmailProvider implements EmailProvider {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String senderEmail;

  @Value("${chronos.web-app.url}")
  private String webAppBaseUrl;

  private static final HashMap<String, String> SOLICITATION_TYPES = new HashMap<>() {
    {
      put("excused_absence", "Faltas");
      put("time_punch_adjusment", "Ajuste de Ponto");
      put("day_off_schedule", "Ajuste de Folga");
      put("day_off", "Folga");
      put("paid_overtime", "Hora Extra Paga");
      put("vacation", "Férias");
      put("withdraw", "Afastamento");
    }
  };

  @Override
  public void sendAuthenticationEmail(Email recipientEmail, Text otpCode) {
    var template = loadEmailTemplate("authentication-email");
    template = template.replace("#{{otpCode}}", otpCode.value());

    sendEmail(recipientEmail.value(), template, "Pedido de autenticação");
  }

  @Override
  public void sendUnexcusedWorkdayAbsenceEmail(Email employeeEmail, Text employeeName, Date workdayDate) {
    var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    var template = loadEmailTemplate("unexcused-workday-absence-email");
    template = template
        .replace("#{{employeeName}}", employeeName.value())
        .replace("#{{workdayDate}}", formatter.format(workdayDate.value()))
        .replace("#{{chronosWebAppUrl}}", webAppBaseUrl);

    sendEmail(employeeEmail.value(), template, "Aviso de falta não justificada");
  }

  @Override
  public void sendSolicitationCreationEmail(Email managerEmail, Text employeeName, Text solicitationType) {
    var solicitationName = getSolicitationName(solicitationType);
    var template = loadEmailTemplate("solicitation-creation-email");
    template = template
        .replace("#{{employeeName}}", employeeName.value())
        .replace("#{{solicitationType}}", solicitationName)
        .replace("#{{chronosWebAppUrl}}", webAppBaseUrl)
        .replace("#{{solicitationsTab}}", solicitationType.value().toLowerCase());

    sendEmail(managerEmail.value(), template, "Solicitação de " + solicitationName);
  }

  @Override
  public void sendSolicitationApprovalEmail(Email employeeEmail, Text employeeName, Text solicitationType) {
    var solicitationName = getSolicitationName(solicitationType);
    var template = loadEmailTemplate("solicitation-approval-email");
    template = template
        .replace("#{{employeeName}}", employeeName.value())
        .replace("#{{solicitationType}}", solicitationName)
        .replace("#{{chronosWebAppUrl}}", webAppBaseUrl)
        .replace("#{{solicitationsTab}}", solicitationType.value().toLowerCase());
    var subject = "Aprovação de solicitação de " + solicitationName;

    sendEmail(employeeEmail.value(), template, subject);
  }

  @Override
  public void sendSolicitationDenialEmail(Email employeeEmail, Text employeeName, Text solicitationType) {
    var solicitationName = getSolicitationName(solicitationType);
    var template = loadEmailTemplate("solicitation-denial-email");
    template = template
        .replace("#{{employeeName}}", employeeName.value())
        .replace("#{{solicitationType}}", solicitationName)
        .replace("#{{chronosWebAppUrl}}", webAppBaseUrl)
        .replace("#{{solicitationsTab}}", solicitationType.value().toLowerCase());
    var subject = "Reprovação de solicitação de " + solicitationName;

    sendEmail(employeeEmail.value(), template, subject);
  }

  private String getSolicitationName(Text solicitationType) {
    return SOLICITATION_TYPES.get(solicitationType.value().toLowerCase());
  }

  private String loadEmailTemplate(String templateName) {
    try {
      var resource = new ClassPathResource("ui/templates/" + templateName + ".html");
      return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new AppException("Email provider exception", e.getMessage());
    }
  }

  private void sendEmail(String recipientEmail, String template, String subject) {
    try {
      var message = mailSender.createMimeMessage();
      var helper = new MimeMessageHelper(message);
      helper.setFrom(senderEmail);
      helper.setTo("69f83229d8-0aa5d8@inbox.mailtrap.io");
      helper.setSubject(subject);
      helper.setText(template, true);
      mailSender.send(message);
      System.out.println("Email sent successfully");
    } catch (MessagingException e) {
      System.out.println("Error sending email: " + e.getMessage());
      throw new AppException("Email provider exception", e.getMessage());
    }
  }
}
