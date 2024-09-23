package org.ait.project.guideline.example.modules.banner.service.core.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.ait.project.guideline.example.blob.modules.storageengine.localstorage.service.StorageService;
import org.ait.project.guideline.example.config.properties.ApplicationProperties;
import org.ait.project.guideline.example.modules.banner.dto.param.BannerParam;
import org.ait.project.guideline.example.modules.banner.dto.request.BannerSortReq;
import org.ait.project.guideline.example.modules.banner.dto.response.BannerRes;
import org.ait.project.guideline.example.modules.banner.exception.BanerSizeEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.BanerSizeOverException;
import org.ait.project.guideline.example.modules.banner.exception.BannerFileEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.BannerNotFoundException;
import org.ait.project.guideline.example.modules.banner.exception.DeeplinkEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.DescriptionEmptyException;
import org.ait.project.guideline.example.modules.banner.exception.FileContentNotImageException;
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
import org.ait.project.guideline.example.shared.utils.UrlBuilderUtils;
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

  private final ApplicationProperties applicationProperties;

  private final UrlBuilderUtils urlBuilderUtils;

  private void validateUploadParam(BannerParam bannerParam,boolean isUpdate) {
    validateParamImage(bannerParam.getFile(),isUpdate);
    validateTitle(bannerParam.getTitle());
    validateDescription(bannerParam.getDescription());
    validateDeeplink(bannerParam.getDeeplink());
  }

  private void validateDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new DescriptionEmptyException();
    }
  }

  private void validateTitle(String title) {
    if (title == null || title.isEmpty()) {
      throw new TitleEmptyException();
    } else {
      if (title.length() > 255) {
        throw new TitleLargerThanException();
      }
    }
  }

  private void validateDeeplink(String deeplink) {
    if (deeplink == null || deeplink.isEmpty()) {
      throw new DeeplinkEmptyException();
    }
  }

  private void validateParamImage(MultipartFile file, boolean isUpdate) {

      if ((file == null || file.isEmpty()) && !isUpdate) {
        throw new BannerFileEmptyException();
      } else {
        if(!(file == null || file.isEmpty())){
          if (file.getSize() >
              (applicationProperties.getImagesConfigProperties().getMaxSizeInMegaByte() * 1024 *
                  1024)) {
            throw new BanerSizeOverException();
          }
          if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new FileNotImageException();
          }
          checkContentFile(file);
        }
      }
  }

  private void checkContentFile(MultipartFile file) {
    try {
      BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
      if (bufferedImage == null) {
        throw new FileContentNotImageException();
      }
    } catch (IOException e) {
      throw new BannerFileEmptyException();
    }
  }

  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> getDetailBanner(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    banner.setImageFile(urlBuilderUtils.createUrlDownloadImage(banner.getImageFile()));
    banner.setThumbnailFile(urlBuilderUtils.createUrlDownloadImage(banner.getThumbnailFile()));
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        bannerMapper.convertToRes(banner));
  }

  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param) {
    validateUploadParam(param,false);
    String fileName = System.currentTimeMillis() + "_" + param.getFile().getOriginalFilename();
    String imageFile = storageService.uploadFile(fileName, param.getFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    String thumbnailFile =
        storageService.uploadFile("Thumbnail_" + fileName, resizeImage(param.getFile()),
            applicationProperties.getThumbnailsProperties().getDirectory());
    Banner banner =
        bannerCommandAdapter.save(bannerMapper.convertToEntity(param, imageFile, thumbnailFile));
    banner.setImageFile(urlBuilderUtils.createUrlDownloadImage(banner.getImageFile()));
    banner.setThumbnailFile(urlBuilderUtils.createUrlDownloadImage(banner.getThumbnailFile()));
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        bannerMapper.convertToRes(banner));
  }

  @Override
  public ResponseEntity<Resource> download(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    ByteArrayResource arrayResource = storageService.downloadFile(banner.getImageFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    return responseHelper.createResponseOctet(banner.getImageFile(), arrayResource);
  }

  @Override
  public ResponseEntity<Resource> downloadThumbnail(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    ByteArrayResource arrayResource = storageService.downloadFile(banner.getThumbnailFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    return responseHelper.createResponseOctet(banner.getThumbnailFile(), arrayResource);
  }


  @Override
  @Transactional
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> update(String id,
                                                                            BannerParam param) {
    validateUploadParam(param,true);
    Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    String imageFile = existingData.getImageFile();
    String thumbnailFile = existingData.getThumbnailFile();
    if(param.getFile() != null && !param.getFile().isEmpty()){
      String fileName = System.currentTimeMillis() + "_" + param.getFile().getOriginalFilename();
      imageFile = storageService.uploadFile(fileName, param.getFile(),
          applicationProperties.getThumbnailsProperties().getDirectory());
      thumbnailFile =
          storageService.uploadFile("Thumbnail_" + fileName, resizeImage(param.getFile()),
              applicationProperties.getThumbnailsProperties().getDirectory());
      deleteFileBanner(existingData);
    }

    Banner updatedData = bannerCommandAdapter.save(
        bannerMapper.update(existingData, param, imageFile, thumbnailFile));

    updatedData.setImageFile(urlBuilderUtils.createUrlDownloadImage(updatedData.getImageFile()));
    updatedData.setThumbnailFile(
        urlBuilderUtils.createUrlDownloadImage(updatedData.getThumbnailFile()));
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
    Page<BannerRes> bannerResPage = bannerQueryAdapter.getPage(pageable, bannerSpecParam)
        .map(banner -> additional(bannerMapper.convertToRes(banner)));
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, bannerResPage,
        bannerResPage.getContent());
  }

  @Override
  public ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllActiveBanner() {
    List<BannerRes> bannerResList =
        bannerQueryAdapter.getAllActiveBanner().stream().map(bannerMapper::convertToRes).toList();
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, null, bannerResList);
  }

  private BannerRes additional(BannerRes response) {
    response.setImageFile(urlBuilderUtils.createUrlDownloadImage(response.getImageFile()));
    response.setThumbnailFile(urlBuilderUtils.createUrlDownloadImage(response.getThumbnailFile()));
    return response;
  }

  private MultipartFile resizeImage(MultipartFile file) {
    MultipartImage multipartImage = null;
    BufferedImage originalImage;

    try {
      originalImage = ImageIO.read(file.getInputStream());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      Thumbnails.of(originalImage)
          .size(applicationProperties.getThumbnailsProperties().getTargetHeight(),
              applicationProperties.getThumbnailsProperties().getTargetHeight())
          .outputFormat(applicationProperties.getThumbnailsProperties().getFormat())
          .outputQuality(applicationProperties.getThumbnailsProperties().getQuality())
          .toOutputStream(outputStream);

      multipartImage =
          new MultipartImage(outputStream.toByteArray(), file.getName() + "-thumbnails",
              file.getOriginalFilename(), file.getContentType(), outputStream.size());

    } catch (IOException e) {
      log.error("Failed to resize image", e);
    }
    return multipartImage;
  }

  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> sortingBanner(
      List<BannerSortReq> bannerSortReqs) {
    if (bannerSortReqs == null) {
      throw new BanerSizeEmptyException();
    }
    bannerCommandAdapter.resortingData(bannerSortReqs);
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, "Success");
  }

  private void deleteFileBanner(Banner existingData) {
    storageService.deleteFile(existingData.getImageFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    storageService.deleteFile(existingData.getThumbnailFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
  }
}
