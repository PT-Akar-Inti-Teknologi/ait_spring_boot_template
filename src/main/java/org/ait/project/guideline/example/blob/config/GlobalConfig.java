package org.ait.project.guideline.example.blob.config;

import lombok.Getter;
import lombok.Setter;
import org.ait.project.guideline.example.blob.shared.enums.PlatformEnum;

@Setter
@Getter
public class GlobalConfig {
  private PlatformEnum platform;
}
