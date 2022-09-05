package com.routediary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.DiaryImage;

@Repository
@Mapper
public interface DiaryImageRepository {
  /**
   * DB로부터 diaryNo에 맞는 이미지 파일들의 정보를 반환 (* 실제 이미지파일은 DB에 저장되지 않고, 서버에 있음)
<<<<<<< HEAD
   *
=======
   * 
>>>>>>> 72c97bd (Feat: diary service, control 기능 완성)
   * @param diaryNo
   * @return List<DiaryImage>
   */
  public List<DiaryImage> selectDiaryImages(int diaryNo);

  /**
   * 다이어리의 이미지정보를 DB에 추가 (* 실제 이미지파일은 DB에 저장되지 않고, 서버에 저장됨)
<<<<<<< HEAD
   *
=======
   * 
>>>>>>> 72c97bd (Feat: diary service, control 기능 완성)
   * @param diaryImage
   */
  public void insert(DiaryImage diaryImage);

  /**
   * 다이어리 이미지정보를 삭제 (* 실제 이미지파일은 DB에 저장되어있지 않기 때문에, 이미지 파일의 정보만 삭제됨)
<<<<<<< HEAD
   *
=======
   * 
>>>>>>> 72c97bd (Feat: diary service, control 기능 완성)
   * @param diaryNo
   * @param imageNo
   */
  public void delete(int diaryNo, int imageNo);

  /**
   * 다이어리의 모든 이미지정보를 삭제 (* 실제 이미지파일은 DB에 저장되어있지 않기 때문에, 이미지 파일의 정보만 삭제됨)
<<<<<<< HEAD
   *
=======
   * 
>>>>>>> 72c97bd (Feat: diary service, control 기능 완성)
   * @param diaryNo
   */
  public void deleteAll(int diaryNo);

}
