package br.com.chronos.core.global.interfaces.providers;
public interface StorageProvider {
  String uploadFile(String fileName,String fileContentType, byte[] fileContent);
  String getFileUrl(String fileKey);
}
