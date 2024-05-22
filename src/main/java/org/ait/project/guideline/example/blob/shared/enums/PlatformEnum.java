package org.ait.project.guideline.example.blob.shared.enums;

import java.util.Arrays;

public enum PlatformEnum {
  LOCAL,
  FIREBASE,
  S3,
  AZURE,
  OSS,
  HCP,
  GCS;

  public static PlatformEnum getEnum(String platform) {
    if (platform == null) {
      return null;
    }
    return Arrays.stream(PlatformEnum.values())
        .filter(platformEnum -> platformEnum.name().equalsIgnoreCase(platform)).findFirst()
        .orElse(null);
  }
}
