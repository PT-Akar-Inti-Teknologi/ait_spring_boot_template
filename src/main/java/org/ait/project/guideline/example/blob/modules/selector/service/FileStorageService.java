package org.ait.project.guideline.example.blob.modules.selector.service;

import org.ait.project.guideline.example.blob.shared.exception.StorageFileNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

  /**
   * Function for upload file using FileStorageService
   *
   * @param fileDir => file directory location for saving item ( can null)
   * @param fileId  => if fieldId filled for file id or file name, will replace file if exists
   * @param file    => Multipart From Request Controller
   * @return String of File ID from services
   */
  String uploadFile(String fileId, MultipartFile file, String fileDir);


  /**
   * Function for get publicUrl for get file from outside (example: image for websites)
   *
   * @param fileDir => file directory location for saving item ( can null)
   * @param fileId  get from uploadFile
   * @return String of URL public for file
   * @throws StorageFileNotFoundException when storage not found
   */
  String getPublicUrlFile(String fileId, String fileDir) throws StorageFileNotFoundException;


  /**
   * Function for download file
   *
   * @param fileDir => file directory location for saving item ( can null)
   * @param fileId  from upload file
   * @return ByteArrayResource used on Response Entity
   * <p>
   * example
   * <pre>
   *    @GetMapping(path = "/download")
   * 	public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request,
   *                                                          @RequestParam(value = "file", required = false) String path,
   * 														  HttpServletResponse response
   * 	) {
   * 		try {
   * 			StorageObject object = googleStorageClientAdapter.download(path);
   *
   *
   * 			byte[] res = Files.toByteArray((File) object.get("file"));
   * 			ByteArrayResource resource = new ByteArrayResource(res);
   *
   * 			return ResponseEntity.ok()
   * 					.contentLength(res.length)
   * 					.header("Content-type", "application/octet-stream")
   * 					.header("Content-disposition", "attachment; filename=\"" + path + "\"").body(resource);
   *        }catch (IOException e) {
   * 			e.printStackTrace();
   * 			throw new RuntimeException("No such file or directory");
   *        }
   *    }
   * </pre>
   * @throws StorageFileNotFoundException when storage not found
   */
  ByteArrayResource downloadFile(String fileId, String fileDir) throws StorageFileNotFoundException;

  /**
   * Function for delete file
   *
   * @param fileDir => file directory location for saving item ( can null)*
   * @param fileId  from upload file
   * @throws StorageFileNotFoundException when storage not found
   */
  void deleteFile(String fileId, String fileDir) throws StorageFileNotFoundException;

}
