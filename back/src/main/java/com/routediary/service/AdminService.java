package com.routediary.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Admin;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.exception.RemoveException;


public interface AdminService {
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
   * 로그인 한다.
   * 
   * @param adminId
   * @param adminPwd
   * @return Admin
   * @throws MismatchException
   * @throws FindException
   */
  public Admin login(String adminId, String adminPwd) throws MismatchException, FindException;

  /**
   * 다이러리를 삭제한다. (관리자는 어떤 다이어리든 삭제할 수 있다)
   * 
   * @param diaryNo
   * @throws RemoveException
   */
  public void removeDiary(int diaryNo) throws RemoveException;

  /**
   * 댓글을 삭제한다. (관리자는 어떤 댓글이든 삭제할 수 있다)
   * 
   * @param diaryNo
   * @param commentNo
   * @throws RemoveException
   */
  public void removeComment(int diaryNo, int commentNo) throws RemoveException;

  /**
   * 공지사항을 작성한다.
   * 
   * @param notice
   * @param imageFiles
   * @throws AddException
   */
  public void writeNotice(Notice notice, List<MultipartFile> imageFiles) throws AddException;

  /**
   * 공지사항을 수정한다.
   * 
   * @param notice
   * @param imageFiles
   * @throws ModifyException
   */
  public void modifyNotice(Notice notice, List<MultipartFile> imageFiles) throws ModifyException;

  /**
   * 공지사항을 삭제한다.
   * 
   * @param noticeNo
   * @throws RemoveException
   */
  public void removeNotice(int noticeNo) throws RemoveException;
}
