package org.ait.project.guideline.example.modules.banner.dto.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BannerParam {

  private MultipartFile file;

  private String title;

  private String description;

  private String deeplink;

  private Boolean isActive;

  public BannerParam(MultipartFile file, String title, String description, String deeplink,
                     Boolean isActive) {
    this.file = file;
    this.title = title;
    this.description = description;
    this.deeplink = deeplink;
    this.isActive = isActive;
  }

}
