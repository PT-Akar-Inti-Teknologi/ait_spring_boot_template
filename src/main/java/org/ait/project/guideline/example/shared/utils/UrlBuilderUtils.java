package org.ait.project.guideline.example.shared.utils;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.properties.DomainConfigProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlBuilderUtils {

  private final DomainConfigProperties domainConfigProperties;

  public String createUrlDownloadImage(String imageId){
    return domainConfigProperties.getUrl() + "/banner/download?id=" + imageId;
  }
  public String createUrlDownloadImageThumbnail(String imageId){
    return domainConfigProperties.getUrl() + "/banner/download-thumbnail?id=" + imageId;
  }

}
