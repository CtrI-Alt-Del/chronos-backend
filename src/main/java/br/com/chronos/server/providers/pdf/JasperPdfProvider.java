package br.com.chronos.server.providers.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.util.ResourceUtils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.chronos.core.global.domain.entities.Responsible;
import br.com.chronos.core.global.domain.exceptions.AppException;
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.DateRange;
import br.com.chronos.core.global.domain.records.Role;
import br.com.chronos.core.work_schedule.domain.dtos.TimeCardRowDto;
import br.com.chronos.core.work_schedule.domain.records.TimeCard;
import br.com.chronos.core.work_schedule.interfaces.PdfProvider;

public class JasperPdfProvider implements PdfProvider {
  private static final String TEMPLATES_FOLDER_PATH = "classpath:jasper/templates";
  private static final String TIME_CARD_JRXML = "time-card.jrxml";
  private static final String CHRONOS_LOGO_PATH = "classpath:images/chronos-logo.png";
  private static final String TIME_CARD_PDF_PATH = "/src/main/resources/jasper/tmp/time-card.pdf";

  public static class TimeCardRow {
    public String date;
    public String firstClockIn;
    public String firstClockOut;
    public String secondClockIn;
    public String secondClockOut;
    public String overtime;
    public String undertime;
    public String latetime;
    public String workedTime;
    public String hourBankCredit;
    public String hourBankDebit;
    public Integer workload;
    public String workdayStatus;

    public TimeCardRow(TimeCardRowDto dto) {
      this.date = formatDate(dto.date);
      this.firstClockIn = formatTime(dto.timePunch.firstClockIn);
      this.firstClockOut = formatTime(dto.timePunch.firstClockOut);
      this.secondClockIn = formatTime(dto.timePunch.secondClockIn);
      this.secondClockOut = formatTime(dto.timePunch.secondClockOut);
      this.overtime = formatTime(dto.overtime);
      this.undertime = formatTime(dto.undertime);
      this.latetime = formatTime(dto.latetime);
      this.workedTime = formatTime(dto.workedTime);
      this.hourBankCredit = formatTime(dto.hourBankCredit);
      this.hourBankDebit = formatTime(dto.hourBankDebit);
      this.workdayStatus = mapWorkdayStatus(dto.workdayStatus);
      this.workload = Integer.valueOf(dto.workload);
    }

    private String formatTime(LocalTime time) {
      if (time == null) {
        return "__:__";
      }
      return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String formatDate(LocalDate date) {
      var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy EEE", Locale.forLanguageTag("pt"));
      return formatter.format(date);
    }

    private String mapWorkdayStatus(String status) {
      var workdayStatuses = new HashMap<String, String>();
      workdayStatuses.put("NORMAL_DAY", "Dia normal");
      workdayStatuses.put("ABSENCE", "Falta");
      workdayStatuses.put("DAY_OFF", "Dia de folga");
      workdayStatuses.put("HOLIDAY", "Feriado");
      workdayStatuses.put("WORK_LEAVE", "Afastamento");
      return workdayStatuses.getOrDefault(status.toUpperCase(), status);
    }

    public String getDate() {
      return date;
    }

    public String getOvertime() {
      return overtime;
    }

    public String getUndertime() {
      return undertime;
    }

    public String getLatetime() {
      return latetime;
    }

    public String getWorkedTime() {
      return workedTime;
    }

    public String getHourBankCredit() {
      return hourBankCredit;
    }

    public String getHourBankDebit() {
      return hourBankDebit;
    }

    public Integer getWorkload() {
      return workload;
    }

    public String getWorkdayStatus() {
      return workdayStatus;
    }

    public String getFirstClockIn() {
      return firstClockIn;
    }

    public String getFirstClockOut() {
      return firstClockOut;
    }

    public String getSecondClockIn() {
      return secondClockIn;
    }

    public String getSecondClockOut() {
      return secondClockOut;
    }
  }

  @Override
  public void generateTimeCardPdf(TimeCard timeCard, DateRange dateRange, Responsible responsible) throws IOException {
    Map<String, Object> parameters = new HashMap<>();

    var chronosLogoBytes = readImageBytes(CHRONOS_LOGO_PATH);
    var timeCardCollectionDataSource = new JRBeanCollectionDataSource(
        timeCard.rows().map(row -> new TimeCardRow(row.getDto())).list());

    parameters.put("chronosLogo", chronosLogoBytes);
    parameters.put("collaboratorEmail", responsible.getEmail().value());
    parameters.put("collaboratorName", responsible.getName().value());
    parameters.put("collaboratorCpf", responsible.getCpf().value());
    parameters.put("collaboratorRole", formatRole(responsible.getRole()));
    parameters.put("collaboratorationSector", responsible.getSector().toString());
    parameters.put("expeditionDate", getExpeditionDate());
    parameters.put("startDate", formatDate(dateRange.startDate()));
    parameters.put("endDate", formatDate(dateRange.endDate()));
    parameters.put("timeCardRows", timeCardCollectionDataSource);

    var sourceFilePath = getSourceFilePath(TIME_CARD_JRXML);
    var timeCardPdfPath = getTimeCardPdfPath();
    try {
      var report = JasperCompileManager.compileReport(sourceFilePath);
      var fillManager = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
      JasperExportManager.exportReportToPdfFile(fillManager, timeCardPdfPath);
    } catch (JRException e) {
      throw new AppException(
          "Jasper PdfProvider Exception",
          "Erro ao gerar pdf de espelho ponto utilizando o JasperReports");
    }
  }

  private String getExpeditionDate() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }

  private String formatRole(Role role) {
    var roleMap = new HashMap<String, String>();
    roleMap.put("MANAGER", "gerente");
    roleMap.put("EMPLOYEE", "funcionário");

    return roleMap.get(role.toString().toUpperCase());
  }

  private String formatDate(Date date) {
    return date.value().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  private byte[] readImageBytes(String imagePath) throws IOException {
    var imageFile = ResourceUtils.getFile(imagePath);
    var file = new File(imageFile.getAbsolutePath());
    try (var fileInputStream = new FileInputStream(file)) {
      var imageBytes = new byte[(int) file.length()];
      fileInputStream.read(imageBytes);
      return imageBytes;
    }
  }

  private String getTimeCardPdfPath() {
    var baseDir = System.getProperty("user.dir");
    return baseDir + TIME_CARD_PDF_PATH;
  }

  private String getSourceFilePath(String filePath) throws FileNotFoundException {
    return ResourceUtils.getFile(TEMPLATES_FOLDER_PATH + '/' + filePath).getAbsolutePath();
  }
}
