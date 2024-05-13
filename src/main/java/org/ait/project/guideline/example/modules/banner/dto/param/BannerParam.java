package org.ait.project.guideline.example.modules.banner.dto.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BannerParam {

    private MultipartFile file;

    private String title;

    private String description;

    public BannerParam(MultipartFile file, String title, String description) {
        this.file = file;
        this.title = title;
        this.description = description;
    }

}
