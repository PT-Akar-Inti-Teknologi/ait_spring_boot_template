package org.ait.project.guideline.example.modules.banner.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BannerRes {

  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("image_file")
  private String imageFile;

  @JsonProperty("thumbnail_file")
  private String thumbnailFile;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  @JsonProperty("deeplink")
  private String deeplink;

  @JsonProperty("index")
  private Integer index;

}
