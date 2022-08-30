package com.routediary.service;

import org.springframework.stereotype.Service;
import com.routediary.dto.Admin;
import com.routediary.dto.Notice;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;


public interface AdminService {
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
  public void writeNotice(Notice notice) throws AddException;
  
  /**
   * 공지사항을 수정한다.
   * @param notice
   * @throws ModifyException
   */
  public void modifyNotice(Notice notice) throws ModifyException;
  
  /**
   * 공지사항을 삭제한다.
   * @param noticeNo
   * @throws RemoveException
   */
  public void removeNotice(int noticeNo) throws RemoveException;
  
}
