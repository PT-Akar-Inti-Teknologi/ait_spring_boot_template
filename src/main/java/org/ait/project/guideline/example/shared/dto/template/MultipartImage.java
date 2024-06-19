package org.ait.project.guideline.example.shared.dto.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class MultipartImage implements MultipartFile {

  String name;
  String originalFilename;
  String contentType;
  boolean isEmpty;
  long size;
  private final byte[] bytes;

  public MultipartImage(byte[] bytes, String name, String originalFilename, String contentType,
                        long size) {
    this.bytes = bytes;
    this.name = name;
    this.originalFilename = originalFilename;
    this.contentType = contentType;
    this.size = size;
    this.isEmpty = false;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getOriginalFilename() {
    return originalFilename;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public boolean isEmpty() {
    return isEmpty;
  }

  @Override
  public long getSize() {
    return size;
  }

  @Override
  public byte[] getBytes() throws IOException {
    return bytes;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void transferTo(File dest) throws IOException, IllegalStateException {
    // TODO Auto-generated method stub

  }
}
