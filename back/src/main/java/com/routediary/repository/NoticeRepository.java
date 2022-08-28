package com.routediary.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.routediary.dto.Notice;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;
@Repository
@Mapper
public interface NoticeRepository {
	/**
	 * 전체 개시물 게수를 가져온다.
	 * @return 전체 개시물 개수 
	 * @throws SelectException
	 */
	public int selectCount() throws SelectException;
	/**
	 * 공지사항을 추가한다.
	 * @param notice 공지사항
	 */
	void insert(Notice notice) throws InsertException;
	/**
	 * 공지사항을 수정한다.
	 * @param notice 공지사항
	 */
	void update(Notice notice) throws UpdateException;
	/**
	 * 공지사항을 삭제한다.
	 * @param noticeNo 공지사항
	 */
	void delete(int noticeNo) throws DeleteException;
	/**
	 * 공지사항 한개를 본다.
	 * @param noticeNo 공지사항번호
	 * @return Notice 공지사항 1개
	 */
	Notice selectNotice(int noticeNo) throws SelectException;
	/**
	 * 공지사항 목록을 본다.
	 * @param startRow,endRow 
	 * @return List<Notice> 공지사항리스트
	 */
	List<Notice> selectNotices(int startRow, int endRow) throws SelectException;
	/**
	 * 공지사항 목록중 검색어가 포함된 공지사항만 본다.
	 * @param keyWord 검색어
	 * @param startRow,endRow 
	 * @return List<Notice> 공지사항리스트
	 */
	List<Notice> selectNoticeskeyWord(String keyWord, int startRow, int endRow) throws SelectException;

}
