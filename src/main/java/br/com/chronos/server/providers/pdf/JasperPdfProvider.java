package br.com.chronos.server.providers.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import br.com.chronos.core.global.domain.entities.Responsible;
import br.com.chronos.core.global.domain.exceptions.AppException;
import br.com.chronos.core.work_schedule.domain.records.TimeCard;
import br.com.chronos.core.work_schedule.interfaces.PdfProvider;

@Service
public class JasperPdfProvider implements PdfProvider {
  private static final String TEMPLATES_FOLDER_PATH = "classpath:jasper/templates";
  private static final String TMP_FOLDER_PATH = "classpath:jasper/tmp";
  private static final String TIME_CARD_JRML = "timecard.jrml";
  private static final String CHRONOS_LOGO_PATH = "classpath:images/chronos_logo.png";

  @Override
  public void generateTimeCardPdf(TimeCard timeCard, Responsible responsible) throws IOException {
    Map<String, Object> parameters = new HashMap<>();

    var chronosLogoBytes = readImageBytes(CHRONOS_LOGO_PATH);

    parameters.put("collaboratorRole", responsible.getRole());
    parameters.put("collaboratorEmail", responsible.getEmail());
    parameters.put("collaboratorName", responsible.getName());
    parameters.put("collaboratorCpf", responsible.getCpf());
    parameters.put("collaboratorSector", responsible.getSector());
    parameters.put("chronosLogo", chronosLogoBytes);

    var sourceFilePath = getSourceFilePath(TIME_CARD_JRML);

    try {
      var report = JasperCompileManager.compileReport(sourceFilePath);
      var fillManager = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
      JasperExportManager.exportReportToPdfFile(fillManager, getTmpFilePath(TMP_FOLDER_PATH));
    } catch (JRException e) {
      throw new AppException(
          "Jasper PdfProvider Exception",
          "Erro ao gerar pdf de espelho ponto utilizando o JasperReports");
    }
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

  private String getTmpFilePath(String fileName) {
    return TMP_FOLDER_PATH.concat(fileName).concat(".pdf");
  }

  private String getSourceFilePath(String jrml) throws FileNotFoundException {
    return ResourceUtils.getFile(TEMPLATES_FOLDER_PATH.concat(jrml)).getAbsolutePath();
  }
}
