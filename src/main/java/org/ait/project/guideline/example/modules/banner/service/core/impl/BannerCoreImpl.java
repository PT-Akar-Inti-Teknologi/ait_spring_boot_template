package org.ait.project.guideline.example.modules.banner.service.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.ait.project.guideline.example.config.properties.ThumbnailsConfigProperties;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.exception.BannerNotFoundException;
import org.ait.project.guideline.example.blob.modules.storageengine.localstorage.service.StorageService;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.service.adapter.command.BannerCommandAdapter;
import org.ait.project.guideline.example.modules.banner.service.adapter.query.BannerQueryAdapter;
import org.ait.project.guideline.example.modules.banner.service.core.BannerCore;
import org.ait.project.guideline.example.modules.banner.transform.BannerMapper;
import org.ait.project.guideline.example.modules.masterdata.dto.param.BannerSpecParam;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.MultipartImage;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerCoreImpl implements BannerCore {

    private final ResponseHelper responseHelper;

    private final BannerQueryAdapter bannerQueryAdapter;

    private final BannerCommandAdapter bannerCommandAdapter;

    private final BannerMapper bannerMapper;

    private final StorageService storageService;

    private final ThumbnailsConfigProperties thumbnailsProperties;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param) {
        String imageFile = storageService.uploadFile(null, param.getFile(), thumbnailsProperties.getDirectory());
        String thumbnailFile = storageService.uploadFile(null, resizeImage(param.getFile()), thumbnailsProperties.getDirectory());
        Banner banner = bannerCommandAdapter.save(bannerMapper.convertToEntity(
                param,
                imageFile,
                thumbnailFile)
        );

        return responseHelper.createResponseDetail(
                ResponseEnum.SUCCESS,
                bannerMapper.convertToRes(banner)
        );
    }

    @Override
    public ResponseEntity<Resource> download(String id) {
        Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        ByteArrayResource arrayResource = storageService.downloadFile(banner.getImageFile(), thumbnailsProperties.getDirectory());
        return responseHelper.createResponseOctet(
                banner.getImageFile(), arrayResource
        );
    }

    @Override
    public ResponseEntity<Resource> downloadThumbnail(String id) {
        Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        return responseHelper.createResponseOctet(
                banner.getTitle(),
                storageService.downloadFile(banner.getId(), banner.getThumbnailFile())
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> update(String id, BannerParam param) {
        Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        String imageFile = storageService.uploadFile(null, param.getFile(), thumbnailsProperties.getDirectory());
        String thumbnailFile = storageService.uploadFile(null, resizeImage(param.getFile()), thumbnailsProperties.getDirectory());
        Banner updatedData = bannerCommandAdapter.save(bannerMapper.update(
                existingData,
                param,
                imageFile,
                thumbnailFile)
        );
        deleteFileBanner(existingData);

        return responseHelper.createResponseDetail(
                ResponseEnum.SUCCESS,
                bannerMapper.convertToRes(updatedData)
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseDetail<Object>>> delete(String id) {
        Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        bannerCommandAdapter.delete(existingData);
        deleteFileBanner(existingData);

        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, null);
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllBanner(Pageable pageable, BannerSpecParam bannerSpecParam) {
        Page<BannerRes> appVersionRequests = bannerQueryAdapter.getPage(pageable, bannerSpecParam)
                .map(bannerMapper::convertToRes);
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, appVersionRequests, appVersionRequests.getContent());
    }

    private MultipartFile resizeImage(MultipartFile file) {
        MultipartImage multipartImage = null;
        BufferedImage originalImage;

        try {
            originalImage = ImageIO.read(file.getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(originalImage)
                    .size(thumbnailsProperties.getTargetHeight(), thumbnailsProperties.getTargetHeight())
                    .outputFormat(thumbnailsProperties.getFormat())
                    .outputQuality(thumbnailsProperties.getQuality())
                    .toOutputStream(outputStream);

            multipartImage = new MultipartImage(outputStream.toByteArray(), file.getName()+"-thumbnails",
                    file.getOriginalFilename(), file.getContentType(), outputStream.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartImage;
    }

    private void deleteFileBanner(Banner existingData){
        storageService.deleteFile(existingData.getImageFile(), thumbnailsProperties.getDirectory());
        storageService.deleteFile(existingData.getThumbnailFile(), thumbnailsProperties.getDirectory());
    }
}