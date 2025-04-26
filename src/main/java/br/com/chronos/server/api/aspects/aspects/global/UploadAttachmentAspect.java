package br.com.chronos.server.api.aspects.aspects.global;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import br.com.chronos.core.solicitation.interfaces.repositories.AttachmentRepository;
import br.com.chronos.core.solicitation.use_cases.UploadJustificationAttachmentUseCase;
import br.com.chronos.server.api.aspects.annotations.global.HandleAttachmentUpload;
import br.com.chronos.server.api.aspects.contexts.global.AttachmentContextHolder;

@Aspect
@Component
public class UploadAttachmentAspect {

  @Autowired
  private StorageProvider storageProvider;

  @Autowired
  private AttachmentRepository attachmentRepository;

  @Around("execution(* br.com.chronos.server.api.controllers.solicitation..*(..))")
  public Object handleAttachment(ProceedingJoinPoint joinPoint) throws Throwable {
    var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    var targetClass = joinPoint.getTarget().getClass();

    boolean hasAnnotationOnMethod = method.isAnnotationPresent(HandleAttachmentUpload.class);
    boolean hasAnnotationOnClass = targetClass.isAnnotationPresent(HandleAttachmentUpload.class);

    if (!hasAnnotationOnMethod && !hasAnnotationOnClass) {
      return joinPoint.proceed();
    }

    try {

      for (Object arg : joinPoint.getArgs()) {
        if (arg instanceof MultipartFile file && !file.isEmpty()) {
          var useCase = new UploadJustificationAttachmentUseCase(storageProvider, attachmentRepository);
          String contentType = file.getContentType();
          if (contentType == null || !contentType.contains("/")) {
            contentType = "application/octet-stream"; // fallback seguro
          }

          var attachmentDto = useCase.execute(file.getOriginalFilename(), contentType, file.getBytes());
          AttachmentContextHolder.set(attachmentDto);
          break;
        }
      }

      return joinPoint.proceed();
    } finally {
      AttachmentContextHolder.clear();
    }
  }
}
