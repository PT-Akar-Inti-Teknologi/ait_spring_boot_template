package org.ait.project.guideline.example.modules.banner.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.library.blob.modules.storageengine.localstorage.service.LocalStorageService;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.exception.BannerNotFoundException;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.service.adapter.command.BannerCommandAdapter;
import org.ait.project.guideline.example.modules.banner.service.adapter.query.BannerQueryAdapter;
import org.ait.project.guideline.example.modules.banner.service.core.BannerCore;
import org.ait.project.guideline.example.modules.banner.transform.BannerMapper;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerCoreImpl implements BannerCore {

    private final ResponseHelper responseHelper;

    private final BannerQueryAdapter bannerQueryAdapter;

    private final BannerCommandAdapter bannerCommandAdapter;

    private final LocalStorageService localStorageService;

    private final BannerMapper bannerMapper;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param) {
        // Create logic to upload file
        // Create logic to resize file to create thumbnail and upload it
        Banner banner = bannerCommandAdapter.save(bannerMapper.convertToEntity(
                param,
                null,
                null)
        );

        return responseHelper.createResponseDetail(
                ResponseEnum.SUCCESS,
                bannerMapper.convertToRes(banner)
        );
    }

    @Override
    public ResponseEntity<Resource> download(String id) {
        Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        return responseHelper.createResponseOctet(
                banner.getTitle(),
                localStorageService.downloadFile(banner.getId(), banner.getImageFile())
        );
    }

    @Override
    public ResponseEntity<Resource> downloadThumbnail(String id) {
        Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        return responseHelper.createResponseOctet(
                banner.getTitle(),
                localStorageService.downloadFile(banner.getId(), banner.getThumbnailFile())
        );
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> update(String id, BannerParam param) {
        // Create logic to upload file and replace it
        // Create logic to resize file to create thumbnail, upload, and replace it

        Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        Banner updatedData = bannerCommandAdapter.save(bannerMapper.update(
                existingData,
                param,
                null,
                null)
        );

        return responseHelper.createResponseDetail(
                ResponseEnum.SUCCESS,
                bannerMapper.convertToRes(updatedData)
        );
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<Object>>> delete(String id) {
        // Create logic to delete file and its thumbnail

        Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        bannerCommandAdapter.delete(existingData);

        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, null);
    }
}
