package br.com.chronos.server.api.controllers.work_schedule.time_card;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.chronos.core.work_schedule.interfaces.PdfProvider;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.use_cases.GenerateTimeCardPdfUseCase;

@TimeCardController
public class GenerateTimeCardPdfController {
  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private PdfProvider pdfProvider;

  @GetMapping("/{collaboratorId}/pdf")
  ResponseEntity<?> handle(
      @PathVariable String collaboratorId,
      @RequestParam int month,
      @RequestParam int year) throws IOException {
    var useCase = new GenerateTimeCardPdfUseCase(workdayLogsRepository, pdfProvider);
    useCase.execute(collaboratorId, month, year);

    var baseDir = System.getProperty("user.dir");
    var filePath = Paths
        .get(baseDir + "/src/main/resources/jasper/tmp")
        .resolve("time-card.pdf").normalize();
    var resource = new UrlResource(filePath.toUri());

    if (!resource.exists()) {
      throw new FileNotFoundException("File not found");
    }

    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
