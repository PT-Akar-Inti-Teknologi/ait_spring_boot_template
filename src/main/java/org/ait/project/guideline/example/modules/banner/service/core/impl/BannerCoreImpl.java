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

  /**
   * Validate param banner before upload banner.
   * @param bannerParam Banner param
   * @param isUpdate Is update or not
   */
  private void validateUploadParam(BannerParam bannerParam,boolean isUpdate) {
    validateParamImage(bannerParam.getFile(),isUpdate);
    validateTitle(bannerParam.getTitle());
    validateDescription(bannerParam.getDescription());
    validateDeeplink(bannerParam.getDeeplink());
  }

  /**
   * Validate description param banner before upload banner.
   * @param description description
   * @throws DescriptionEmptyException if description is null or empty
   */
  private void validateDescription(String description) {
    if (description == null || description.isEmpty()) {
      throw new DescriptionEmptyException();
    }
  }

  /**
   * Validate title param banner before upload banner.
   * @param title Title
   * @throws TitleEmptyException if title is null or empty
   * @throws TitleLargerThanException if length of title more than 255
   */
  private void validateTitle(String title) {
    if (title == null || title.isEmpty()) {
      throw new TitleEmptyException();
    } else {
      if (title.length() > 255) {
        throw new TitleLargerThanException();
      }
    }
  }

  /**
   * Validate deeplink param banner before upload banner.
   * @param deeplink Deeplink
   * @throws DeeplinkEmptyException if deeplink is null or empty
   */
  private void validateDeeplink(String deeplink) {
    if (deeplink == null || deeplink.isEmpty()) {
      throw new DeeplinkEmptyException();
    }
  }

  /**
   * Validate param image before upload banner.
   * @param file MultipartFile that contains image
   * @param isUpdate boolean that indicate if the banner is update or create
   * @throws BannerFileEmptyException if file is null or empty and it is not update
   * @throws BanerSizeOverException if size of file more than 5MB
   * @throws FileNotImageException if file is not image
   */
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

  /**
   * Validate the content of a file to ensure it is an image file.
   * @param file the file to be validated
   * @throws FileContentNotImageException if the content of the file is not an image
   * @throws BannerFileEmptyException if an IOException occurs while reading the file
   */
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

  /**
   * Get detail banner by id.
   * @param id the id of the banner
   * @return ResponseEntity of ResponseTemplate with ResponseDetail of BannerRes
   * @throws BannerNotFoundException if banner with given id is not found
   */
  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> getDetailBanner(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        additional(bannerMapper.convertToRes(banner)));
  }

  /**
   * Upload a banner.
   * @param param the banner param
   * @return ResponseEntity of ResponseTemplate with ResponseDetail of BannerRes
   * @throws BannerFileEmptyException if an IOException occurs while reading the file
   * @throws FileContentNotImageException if the content of the file is not an image
   * @throws FileNotImageException if the file is not an image
   * @throws DeeplinkEmptyException if deeplink is empty
   * @throws DescriptionEmptyException if description is empty
   * @throws TitleEmptyException if title is empty
   * @throws TitleLargerThanException if title is larger than 50 characters
   * @throws BanerSizeEmptyException if banner size is empty
   * @throws BanerSizeOverException if banner size is over the limit
   */
  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<BannerRes>>> upload(BannerParam param) {
    validateUploadParam(param,false);
    String fileName = System.currentTimeMillis() + "_" + param.getFile().getOriginalFilename();
    String imageFile = storageService.uploadFile(fileName, param.getFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    String thumbnailFile =
        storageService.uploadFile("Thumbnail__" + fileName, resizeImage(param.getFile()),
            applicationProperties.getThumbnailsProperties().getDirectory());
    Banner banner =
        bannerCommandAdapter.save(bannerMapper.convertToEntity(param, imageFile, thumbnailFile));
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        additional(bannerMapper.convertToRes(banner)));
  }

  /**
   * Download a banner by its id.
   * @param id the id of the banner to download
   * @return ResponseEntity of Resource with the image file
   * @throws BannerNotFoundException if the banner is not found
   */
  @Override
  public ResponseEntity<Resource> download(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    ByteArrayResource arrayResource = storageService.downloadFile(banner.getImageFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    return responseHelper.createResponseOctet(banner.getImageFile(), arrayResource);
  }

  /**
   * Download a thumbnail of a banner by its id.
   * @param id the id of the banner to download the thumbnail
   * @return ResponseEntity of Resource with the thumbnail image file
   * @throws BannerNotFoundException if the banner is not found
   */
  @Override
  public ResponseEntity<Resource> downloadThumbnail(String id) {
    Banner banner = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    ByteArrayResource arrayResource = storageService.downloadFile(banner.getThumbnailFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    return responseHelper.createResponseOctet(banner.getThumbnailFile(), arrayResource);
  }


  /**
   * Update a banner by its id.
   * @param id the id of the banner to update
   * @param param the parameters to update the banner
   * @return ResponseEntity of ResponseTemplate with the updated banner
   * @throws BannerNotFoundException if the banner is not found
   * @throws BannerFileEmptyException if the file is null or empty
   * @throws BanerSizeEmptyException if the file size is empty
   * @throws BanerSizeOverException if the file size is over the limit
   * @throws DeeplinkEmptyException if the deeplink is null or empty
   * @throws DescriptionEmptyException if the description is null or empty
   */
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
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
        additional(bannerMapper.convertToRes(updatedData)));
  }

  /**
   * Delete a banner by the given id
   * @param id the id of the banner to be deleted
   * @return ResponseEntity of ResponseTemplate with the deleted banner
   * @throws BannerNotFoundException if the banner is not found
   */
  @Override
  @Transactional
  public ResponseEntity<ResponseTemplate<ResponseDetail<Object>>> delete(String id) {
    Banner existingData = bannerQueryAdapter.getById(id).orElseThrow(BannerNotFoundException::new);
    bannerCommandAdapter.delete(existingData);
    deleteFileBanner(existingData);

    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, null);
  }

  /**
   * Get all banners with filter and pagination
   * @param pageable the pagination configuration
   * @param bannerSpecParam the filter configuration
   * @return ResponseEntity of ResponseTemplate with the collection of BannerRes
   */
  @Override
  public ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllBanner(
      Pageable pageable, BannerSpecParam bannerSpecParam) {
    Page<BannerRes> bannerResPage = bannerQueryAdapter.getPage(pageable, bannerSpecParam)
        .map(banner -> additional(bannerMapper.convertToRes(banner)));
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, bannerResPage,
        bannerResPage.getContent());
  }

  /**
   * Get all active banners.
   * @return ResponseEntity of ResponseTemplate with the collection of BannerRes
   */
  @Override
  public ResponseEntity<ResponseTemplate<ResponseCollection<BannerRes>>> getAllActiveBanner() {
    List<BannerRes> bannerResList =
        bannerQueryAdapter.getAllActiveBanner().stream().map(banner -> additional(bannerMapper.convertToRes(banner))).toList();
    return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, null, bannerResList);
  }

  /**
   * Adds image and thumbnail download URLs to the given BannerRes.
   * @param response the BannerRes to add URLs to
   * @return the BannerRes with the added URLs
   */
  private BannerRes additional(BannerRes response) {
    response.setImageFile(urlBuilderUtils.createUrlDownloadImage(response.getId()));
    response.setThumbnailFile(urlBuilderUtils.createUrlDownloadImageThumbnail(response.getId()));
    return response;
  }

  /**
   * Resize the given MultipartFile image based on the application properties.
   * The following properties are used:
   * - targetHeight: The height of the resized image.
   * - format: The image format of the resized image.
   * - quality: The quality of the resized image.
   * @param file The MultipartFile image to resize.
   * @return The resized MultipartFile image.
   */
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

  /**
   * This method is used to sort the banner data. The banner data should be wrapped
   * inside a List of BannerSortReq. The banner data is sorted based on the index
   * value of the BannerSortReq. The banner that has the lowest index value will
   * be placed first.
   * @param bannerSortReqs The list of BannerSortReq that contains the banner
   * data to be sorted.
   * @return The response of the sorting banner operation. If the sorting is
   * successful, the response will be a successful response with a message of
   * "Success". If the sorting is failed, the response will be a failed response
   * with a message of "Failed".
   */
  @Override
  public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> sortingBanner(
      List<BannerSortReq> bannerSortReqs) {
    if (bannerSortReqs == null) {
      throw new BanerSizeEmptyException();
    }
    bannerCommandAdapter.resortingData(bannerSortReqs);
    return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, "Success");
  }

  /**
   * Delete the banner image and thumbnail from the file system.
   *
   * <p>This method is used to delete the banner image and thumbnail from the file
   * system when the banner is deleted.
   *
   * @param existingData The banner data.
   */
  private void deleteFileBanner(Banner existingData) {
    storageService.deleteFile(existingData.getImageFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
    storageService.deleteFile(existingData.getThumbnailFile(),
        applicationProperties.getThumbnailsProperties().getDirectory());
  }
}
