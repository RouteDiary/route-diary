package com.routediary.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Admin;
import com.routediary.dto.Diary;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;


public interface AdminService {
  public Diary showDiary(int diaryNo) throws FindException;
  /**
   * 다이어리 목록을  1 - diary_view_cnt
   *              2 - diary_writing_time
   *              3 - diary_like_cnt 
   *              DESC 순으로 정
   * @param order
   * @param currentPage
   * @param hashtags
   * @return PageBean<Diary>
   * @throws FindException
   */
  public PageBean<Diary> showDiaryBoard(int order,int currentPage,List<String>hashtags) throws FindException;
  /**
   * 로그인 한다.
   * @param adminId
   * @param adminPwd
   * @return Admin 
   * @throws FindException
   */
  public Admin login(String adminId,String adminPwd) throws FindException;
  
  /**
   * 다이러리를 삭제한다.
   * @param diaryNo 
   * @throws RemoveException
   */
  public void removeDiary(int diaryNo) throws RemoveException;
  
  /**
   * 한개의 다이어리에 해당하는 모든 댓글을 삭제한다.
   * @param diaryNo
   * @param commentNo
   * @throws RemoveException
   */
  public void removeComment(int diaryNo,int commentNo) throws RemoveException;
  
  /**
   * 공지사항 작성한다.
   * @param notice
   * @throws AddException
   */
//  public void writeNotice(Notice notice) throws AddException;
  
  /**
   * 공지사항을 수정한다.
   * @param notice
   * @throws ModifyException
   */
//  public void modifyNotice(Notice notice) throws ModifyException;
  
  /**
   * 공지사항을 삭제한다.
   * @param noticeNo
   * @throws RemoveException
   */
  public void removeNotice(int noticeNo) throws RemoveException;
  /**
   * 공지사항을 작성한다.
   * @param notice
   * @param imageFiles
   * @throws AddException
   */
  public void writeNotice(Notice notice, List<MultipartFile> imgFiles) throws AddException;
  /**
   * 공지사항을 수정한다.
   * @param notice
   * @param imgFiles
   * @throws ModifyException
   */
  public void modifyNotice(Notice notice, List<MultipartFile> imgFiles) throws ModifyException;
  
}
