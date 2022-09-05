package com.routediary.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.NoticeImage;

@SpringBootTest
class NoticeImageRepositoryTest {

  @Autowired
  NoticeImageRepository noticeImageRepository;

  @Test
  void selectNoticeImagesTest() throws Exception {
    int noticeNo = 1;
    int expectedSize = 2;
    String expectedFileName = "example.jpg";
    String expectedStoredFilePath = "C://example";
    List<NoticeImage> noticeImages = noticeImageRepository.selectNoticeImages(noticeNo);
    assertEquals(expectedSize, noticeImages.size());
    assertEquals(expectedFileName, noticeImages.get(0).getFileName());
    assertEquals(expectedStoredFilePath, noticeImages.get(0).getStoredFilePath());
  }

  @Test
  void insertTest() throws Exception {
    int noticeNo = 1;
    String fileName = "newfile.png";
    String storedFilePath = "C://newexample";

    NoticeImage noticeImage = new NoticeImage();
    noticeImage.setNoticeNo(noticeNo);
    noticeImage.setFileName(fileName);
    noticeImage.setStoredFilePath(storedFilePath);

    noticeImageRepository.insert(noticeImage);
    List<NoticeImage> noticeImages = noticeImageRepository.selectNoticeImages(noticeNo);
    assertEquals(fileName, noticeImages.get(2).getFileName());
    assertEquals(storedFilePath, noticeImages.get(2).getStoredFilePath());
  }

  @Test
  void deleteTest() throws Exception {
    int noticeNo = 1;
    int imageNo = 1;

    noticeImageRepository.delete(noticeNo, imageNo);
    List<NoticeImage> noticeImages = noticeImageRepository.selectNoticeImages(noticeNo);
    assertNull(noticeImages);
  }

  @Test
  void deleteAllTest() throws Exception {
    int noticeNo = 1;

    noticeImageRepository.deleteAll(noticeNo);
    List<NoticeImage> noticeImages = noticeImageRepository.selectNoticeImages(noticeNo);
    assertNull(noticeImages);
  }
}

