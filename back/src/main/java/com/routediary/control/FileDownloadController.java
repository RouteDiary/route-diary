package com.routediary.control;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.routediary.enums.Dto;
import com.routediary.enums.ErrorCode;
import com.routediary.exception.InvalidActionException;
import com.routediary.functions.ServiceFunctions;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping
public class FileDownloadController {
  @Autowired
  ServiceFunctions serviceFunctions;

  @GetMapping("/imagedownload")
  public ResponseEntity<?> downloadImage(int postNo, String dto, String imageFileName)
      throws InvalidActionException, IOException {
    String path = null;
    dto = dto.toLowerCase();
    if (dto.equals("diary")) {
      path = serviceFunctions.findOriginalPath(postNo, Dto.DIARY);
    } else if (dto.equals("notice")) {
      path = serviceFunctions.findOriginalPath(postNo, Dto.NOTICE);
    } else {
      throw new InvalidActionException(ErrorCode.INVALID_DTO_NAME);
    }
    File imageFile = new File(path, imageFileName);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set(HttpHeaders.CONTENT_LENGTH, imageFile.length() + "");
    responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imageFile.toPath()));
    responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
        "inline; filename=" + URLEncoder.encode("a", "UTF-8"));
    log.info("다운로드된 이미지파일 : " + path + imageFileName);
    return new ResponseEntity<>(FileCopyUtils.copyToByteArray(imageFile), responseHeaders,
        HttpStatus.OK);
  }
}
