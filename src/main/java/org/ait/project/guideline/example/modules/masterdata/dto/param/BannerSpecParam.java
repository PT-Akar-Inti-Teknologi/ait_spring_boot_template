package org.ait.project.guideline.example.modules.masterdata.dto.param;

import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class BannerSpecParam {


    private String search;

    @ConstructorProperties({"search"})
    public BannerSpecParam(String search) {
        this.search = search != null ? search : "";
    }
}
