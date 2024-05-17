package org.ait.project.guideline.example.modules.banner.service.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.ait.project.guideline.example.config.properties.ThumbnailsConfigProperties;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.exception.BannerNotFoundException;
import org.ait.project.guideline.example.modules.banner.model.jpa.entity.Banner;
import org.ait.project.guideline.example.modules.banner.service.adapter.command.BannerCommandAdapter;
import org.ait.project.guideline.example.modules.banner.service.adapter.query.BannerQueryAdapter;
import org.ait.project.guideline.example.modules.banner.service.core.BannerCore;
import org.ait.project.guideline.example.modules.banner.transform.BannerMapper;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.MultipartImage;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerCoreImpl implements BannerCore {

    private final ResponseHelper responseHelper;

    private final BannerQueryAdapter bannerQueryAdapter;

    private final BannerCommandAdapter bannerCommandAdapter;


    private final BannerMapper bannerMapper;

    private final ThumbnailsConfigProperties thumbnailsConfigProperties;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param) {
        // Create logic to upload file

        // Create logic to resize file to create thumbnail and upload it
        MultipartFile file = resizeImage(param.getFile());
        String imageThumbnail = file.getOriginalFilename();
        Banner banner = bannerCommandAdapter.save(bannerMapper.convertToEntity(
                param,
                null,
                imageThumbnail)
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
                banner.getTitle(), null
//                localStorageService.downloadFile(banner.getId(), banner.getImageFile())
        );
    }

    @Override
    public ResponseEntity<Resource> downloadThumbnail(String id) {
        Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
        return responseHelper.createResponseOctet(
                banner.getTitle(), new ByteArrayResource(banner.getThumbnailFile().getBytes(StandardCharsets.UTF_8))
//                localStorageService.downloadFile(banner.getId(), banner.getThumbnailFile())
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

    private MultipartFile resizeImage(MultipartFile file) {
        MultipartImage multipartImage = null;
        BufferedImage originalImage;

        try {
            File img = new File("pathFile");
            originalImage = ImageIO.read(img );
            log.info("Image : "+ originalImage);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(thumbnailsConfigProperties.getTargetHeight(), thumbnailsConfigProperties.getTargetHeight())
                .outputFormat(thumbnailsConfigProperties.getFormat())
                .outputQuality(thumbnailsConfigProperties.getQuality())
                .toOutputStream(outputStream);

            multipartImage = new MultipartImage(outputStream.toByteArray(), file.getName()+"-thumbnails",
                    file.getOriginalFilename(), file.getContentType(), outputStream.size());

        } catch (IOException e) {
            log.error("Error File to Image");
            e.printStackTrace();
        }
        return multipartImage;
    }
}
