package org.ait.project.guideline.example.modules.banner.service.core.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.ait.project.guideline.example.blob.modules.storageengine.localstorage.service.StorageService;
import org.ait.project.guideline.example.config.properties.ThumbnailsConfigProperties;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.exception.BannerFileEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.BannerNotFoundException;
import org.ait.project.guideline.example.modules.banner.exception.DescriptionEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.FileNotImageException;
import org.ait.project.guideline.example.modules.banner.exception.TitleEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.TitleLargerThanException;
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

  private void validateUploadParam(BannerParam bannerParam){
    validateTitle(bannerParam.getTitle());
    validateDescription(bannerParam.getDescription());
    validateParamImage(bannerParam.getFile());
  }

  private void validateDescription(String description) {
    if(description == null || description.isEmpty()){
      throw new DescriptionEmptyException();
    }
  }

  private void validateTitle(String title) {
    if(title == null || title.isEmpty()){
      throw new TitleEmptyException();
    }else {
      if(title.length() > 255){
        throw new TitleLargerThanException();
      }
    }
  }

  private void validateParamImage(MultipartFile file){
    if(file == null){
      throw new BannerFileEmptyException();
    }else {
      if(Objects.requireNonNull(file.getContentType()).startsWith("image/")){
        throw new FileNotImageException();
      }
    }
  }

  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param) {
    validateUploadParam(param);
    String fileName = System.currentTimeMillis() + "_" + param.getFile().getOriginalFilename();
    String imageFile =
        storageService.uploadFile(fileName, param.getFile(), thumbnailsProperties.getDirectory());
    String thumbnailFile =
        storageService.uploadFile("Thumbnail_" + fileName, resizeImage(param.getFile()),
            thumbnailsProperties.getDirectory());
    Banner banner =
        bannerCommandAdapter.save(bannerMapper.convertToEntity(param, imageFile, thumbnailFile));

    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        bannerMapper.convertToRes(banner));
  }

  @Override
  public ResponseEntity<Resource> download(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    ByteArrayResource arrayResource =
        storageService.downloadFile(banner.getImageFile(), thumbnailsProperties.getDirectory());
    return responseHelper.createResponseOctet(banner.getImageFile(), arrayResource);
  }

  @Override
  public ResponseEntity<Resource> downloadThumbnail(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    ByteArrayResource arrayResource =
        storageService.downloadFile(banner.getThumbnailFile(), thumbnailsProperties.getDirectory());
    return responseHelper.createResponseOctet(banner.getThumbnailFile(), arrayResource);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> update(String id,
                                                                            BannerParam param) {
    validateUploadParam(param);
    Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    String fileName = System.currentTimeMillis() + "_" + param.getFile().getOriginalFilename();
    String imageFile =
        storageService.uploadFile(fileName, param.getFile(), thumbnailsProperties.getDirectory());
    String thumbnailFile =
        storageService.uploadFile("Thumbnail_" + fileName, resizeImage(param.getFile()),
            thumbnailsProperties.getDirectory());

    Banner updatedData = bannerCommandAdapter.save(
        bannerMapper.update(existingData, param, imageFile, thumbnailFile));
    deleteFileBanner(existingData);

    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        bannerMapper.convertToRes(updatedData));
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
  public ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllBanner(
      Pageable pageable, BannerSpecParam bannerSpecParam) {
    Page<BannerRes> appVersionRequests =
        bannerQueryAdapter.getPage(pageable, bannerSpecParam).map(bannerMapper::convertToRes);
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, appVersionRequests,
        appVersionRequests.getContent());
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
          .outputQuality(thumbnailsProperties.getQuality()).toOutputStream(outputStream);

      multipartImage =
          new MultipartImage(outputStream.toByteArray(), file.getName() + "-thumbnails",
              file.getOriginalFilename(), file.getContentType(), outputStream.size());

    } catch (IOException e) {
      log.error("Failed to resize image", e);
    }
    return multipartImage;
  }
  private void deleteFileBanner(Banner existingData) {
    storageService.deleteFile(existingData.getImageFile(), thumbnailsProperties.getDirectory());
    storageService.deleteFile(existingData.getThumbnailFile(), thumbnailsProperties.getDirectory());
  }
}
