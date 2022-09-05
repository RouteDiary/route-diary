package com.routediary.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.DiaryImage;

@SpringBootTest
class DiaryImageRepositoryTest {

  @Autowired
  DiaryImageRepository diaryImageRepository;

  @Test
  void selectDiaryImagesTest() throws Exception {
    int diaryNo = 1;
    int expectedSize = 2;
    String expectedFileName = "example.jpg";
    String expectedStoredFilePath = "C://example";
    List<DiaryImage> diaryImages = diaryImageRepository.selectDiaryImages(diaryNo);
    assertEquals(expectedSize, diaryImages.size());
    assertEquals(expectedFileName, diaryImages.get(0).getFileName());
    assertEquals(expectedStoredFilePath, diaryImages.get(0).getStoredFilePath());
  }

  @Test
  void insertTest() throws Exception {
    int diaryNo = 1;
    String fileName = "newfile.png";
    String storedFilePath = "C://newexample";

    DiaryImage diaryImage = new DiaryImage();
    diaryImage.setDiaryNo(diaryNo);
    diaryImage.setFileName(fileName);
    diaryImage.setStoredFilePath(storedFilePath);

    diaryImageRepository.insert(diaryImage);
    List<DiaryImage> diaryImages = diaryImageRepository.selectDiaryImages(diaryNo);
    assertEquals(fileName, diaryImages.get(2).getFileName());
    assertEquals(storedFilePath, diaryImages.get(2).getStoredFilePath());
  }

  @Test
  void deleteTest() throws Exception {
    int diaryNo = 1;
    int imageNo = 1;

    diaryImageRepository.delete(diaryNo, imageNo);
    List<DiaryImage> diaryImages = diaryImageRepository.selectDiaryImages(diaryNo);
    assertNull(diaryImages);
  }

  @Test
  void deleteAllTest() throws Exception {
    int diaryNo = 2;
    diaryImageRepository.deleteAll(diaryNo);
    List<DiaryImage> diaryImages = diaryImageRepository.selectDiaryImages(diaryNo);
    assertNull(diaryImages);
  }
}

