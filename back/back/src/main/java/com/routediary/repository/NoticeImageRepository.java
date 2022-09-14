package com.routediary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.NoticeImage;

@Repository
@Mapper
public interface NoticeImageRepository {
  /**
   * DB로부터 noticeNo에 맞는 이미지 파일들의 정보를 반환 (* 실제 이미지파일은 DB에 저장되지 않고, 서버에 있음)
   *
   * @param noticeNo
   * @return List<NoticeImage>
   */
  public List<NoticeImage> selectNoticeImages(int noticeNo);

  /**
   * 공지사항의 이미지정보를 DB에 추가 (* 실제 이미지파일은 DB에 저장되지 않고, 서버에 저장됨)
   *
   * @param noticeImage
   */
  public void insert(NoticeImage noticeImage);

  /**
   * 공지사항의 이미지정보를 삭제 (* 실제 이미지파일은 DB에 저장되어있지 않기 때문에, 이미지 파일의 정보만 삭제됨)
   *
   * @param noticeNo
   * @param imageNo
   */
  public void delete(int noticeNo, int imageNo);

  /**
   * 공지사항의 모든 이미지정보를 삭제 (* 실제 이미지파일은 DB에 저장되어있지 않기 때문에, 이미지 파일의 정보만 삭제됨)
   *
   * @param noticeNo
   */
  public void deleteAll(int noticeNo);
}
