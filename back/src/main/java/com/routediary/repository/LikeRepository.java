package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.routediary.dto.Like;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
@Repository
@Mapper
public interface LikeRepository {
	/**
	 * 좋아요 추가한다.
	 * @param like  
	 * @throws InsertException
	 */
	void insert(Like like) throws InsertException;
	
	/**  
	 * 좋아요 삭제한다.
	 * @param like
	 * @throws DeleteException
	 */
	void delete(Like like) throws DeleteException;

	/**
	 * 좋아요가 눌린 다이어리 번호를 찾아 전체 삭제한다.
	 * @param diaryNo
	 * @throws DeleteException
	 */
	void deleteAll (int diaryNo) throws DeleteException;
}
