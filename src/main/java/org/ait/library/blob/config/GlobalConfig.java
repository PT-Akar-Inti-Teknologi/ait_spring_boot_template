package org.ait.library.blob.config;

import lombok.Getter;
import lombok.Setter;
import org.ait.library.blob.shared.enums.PlatformEnum;

@Setter
@Getter
public class GlobalConfig {
  private PlatformEnum platform;
}
