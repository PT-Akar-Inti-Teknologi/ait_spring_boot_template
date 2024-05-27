package org.ait.project.guideline.example.blob.modules.storageengine.localstorage.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalStorageCannotAccessPublicException extends RuntimeException {
  public LocalStorageCannotAccessPublicException() {
    super("File cannot Access from Public");
    log.error("File Cannot Access from Public");
  }
}
