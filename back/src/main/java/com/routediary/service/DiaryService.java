package com.routediary.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Like;
import com.routediary.dto.PageBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.InvalidActionException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.exception.RemoveException;

public interface DiaryService {
  /**
   * 다이어리를 작성한다. (다이어리 작성시, diary_modifying_time은 null)
   * 
   * @param diary
   * @param imageFiles
   * @throws AddException
   */
  public void writeDiary(Diary diary, List<MultipartFile> imageFiles) throws AddException;


  /**
   * 다이어리를 수정한다. (다이어리 제목, 여행일자, 공개여부를 수정할 수 있고, 다이어리의 루트, 루트이미지, 해시태그를 추가/수정/삭제할 수 있음) (다이어리 수정시,
   * diary_modifying_time을 현재시간으로 설정. diary_writing_time은 바꾸지 않음)
   * 
   * @param diary
   * @param imageFiles
   * @throws ModifyException
   */
  public void modifyDiary(Diary diary, List<MultipartFile> imageFiles) throws ModifyException;

  /**
   * 다이어리를 삭제한다. (다이어리와 연결된 루트, 해시태그, 댓글, 좋아요도 모두 삭제됨)
   * 
   * @param diaryNo
   * @throws RemoveException
   */
  public void removeDiary(int diaryNo) throws RemoveException;

  /**
   * 댓글을 작성한다.
   * 
   * @param comment
   * @throws AddException
   */
  public void writeComment(Comment comment) throws AddException;

  /**
   * 댓글을 수정한다. (comment_writing_time도 현재시간으로 바꾼다)
   * 
   * @param comment
   * @throws ModifyException
   */
  public void modifyComment(Comment comment) throws ModifyException;

  /**
   * 댓글을 삭제한다.
   * 
   * @param diaryNo
   * @param commentNo
   * @throws RemoveException
   */
  public void removeComment(int diaryNo, int commentNo) throws RemoveException;

  /**
   * isLike = true 인 경우, 좋아요 +1, isLike = false인 경우, 좋아요 -1
   * 
   * @param isLike
   * @param like
   * @throws InvalidActionException
   * @throws RemoveException
   * @throws AddException
   */
  public void clickLikeToggle(boolean isLike, Like like)
      throws AddException, RemoveException, InvalidActionException;

  /**
   * 페이지에 해당하는 다이어리들을 반환한다. (비공개상태의 다이어리는 제외)
   * 
   * order parameter : 1 = diary_view_cnt의 DESC 순 2 = diary_writing_time의 DESC 순 3 = diary_like_cnt의
   * DESC 순 으로 정렬하여 가져옴
   * 
   * hashtags parameter가 null인 경우, 전체 다이어리에서 다이어리들을 가져온다. hashtags parameter가 null이 아닌 경우, hashtag로
   * 검색한 다이어리들을 가져온다.
   * 
   * @param order
   * @param currentPage
   * @param hashtags
   * @return PageBean<Diary>
   * @throws FindException
   * @throws NumberNotFoundException
   */
  public PageBean<Diary> showDiaryBoard(int order, int currentPage, List<String> hashtags)
      throws FindException, NumberNotFoundException;


  /**
   * 공개여부와 상관없이 페이지에 해당하는 내가 작성한 다이어리들을 반환한다.
   * 
   * @param currentPage
   * @param clientId
   * @return PageBean<Diary>
   * @throws FindException
   */
  public PageBean<Diary> showMyDiaryBoard(int currentPage, String clientId) throws FindException;

  /**
   * Diary(다이어리) 객체를 반환한다. (화면에 띄우기 위해 필요한 모든 정보를 담고 있음)
   * 
   * @param diaryNo
   * @return Diary
   * @throws FindException
   * @throws NumberNotFoundException
   */
  public Diary showDiary(int diaryNo) throws FindException, NumberNotFoundException;

  /**
   * 각각 좋아요(diary_like_cnt)순, 작성일자(diary_writing_time)순으로 상위 5개의 다이어리를 반환 (베스트 다이어리)
   * 
   * @return Map<String, List<Diary>>
   * @throws FindException
   */
  public Map<String, List<Diary>> showIndexPage() throws FindException;

}
