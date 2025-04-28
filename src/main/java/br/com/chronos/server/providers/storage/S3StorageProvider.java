package br.com.chronos.server.providers.storage;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import br.com.chronos.core.global.domain.exceptions.AppException;
import br.com.chronos.core.global.interfaces.providers.EnvProvider;
import br.com.chronos.core.global.interfaces.providers.StorageProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3StorageProvider implements StorageProvider {
  private final S3Client s3Client;


  @Value("${s3.bucket.name}")
  private String bucketName;

  public static class S3Config {
    private final String acessKey;
    private final String secretKey;
    private final String region;

    public S3Config(String acessKey, String secretKey, String region) {
      this.acessKey = acessKey;
      this.secretKey = secretKey;
      this.region = region;
    }

    public String getAcessKey() {
      return acessKey;
    }

    public String getSecretKey() {
      return secretKey;
    }

    public String getRegion() {
      return region;
    }
  }

  public S3StorageProvider(EnvProvider envProvider) {
    var acessKey = envProvider.get("S3_ACCESS_KEY");
    var secretKey = envProvider.get("S3_SECRET_ACCESS_KEY");
    var region = envProvider.get("S3_REGION");
    
    if (acessKey == null || secretKey == null || region == null) {
      throw new IllegalArgumentException("AWS credentials and region must be provided");
    }
    var config = new S3Config(acessKey, secretKey, region);
    this.s3Client = buildS3Client(config);
  }

  private static S3Client buildS3Client(S3Config config) {
    var credentials = AwsBasicCredentials.create(config.getAcessKey(), config.getSecretKey());
    var serviceConfiguration = S3Configuration.builder().pathStyleAccessEnabled(true).build();
    return S3Client
        .builder()
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .region(Region.of(config.getRegion())).serviceConfiguration(serviceConfiguration)
        .build();
  }

  @Override
  public String uploadFile(String fileName, String fileContentType, byte[] fileContent) {
    var fileKey = UUID.randomUUID() + "-" + fileName;
    try {
      var objectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key("organization/" + fileKey)
          .contentType(fileContentType)
          .build();
      var requestBody = RequestBody.fromByteBuffer(ByteBuffer.wrap(fileContent));
      s3Client.putObject(objectRequest, requestBody);
      return fileKey;
    } catch (Exception exception) {
      throw new AppException("StorageException");
    }
  }

  @Override
  public String getFileUrl(String fileKey) {
    GetUrlRequest urlRequest = GetUrlRequest.builder()
        .bucket(bucketName)
        .key("organization/" + fileKey)
        .build();
    return s3Client.utilities().getUrl(urlRequest).toString();
  }
}
