package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Route;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;


@Repository
@Mapper
public interface RouteRepository {

  /**
   * 각 다이어리에 속한 루트의 개수를 반환
   * 
   * @param diaryNo
   * @return int
   * @throws SelectException
   */
  public int selectCount(int diaryNo) throws SelectException;

  /**
   * Route(루트) 객체를 DB에 추가
   * 
   * @param route
   * @throws InsertException
   */
  public void insert(Route route) throws InsertException;

  /**
   * diaryNo, routeNo가 일치하는 루트를 삭제한다
   * 
   * @param diaryNo
   * @param routeNo
   * @throws DeleteException
   */
  public void delete(int diaryNo, int routeNo) throws DeleteException;

  /**
   * 해당 diaryNo의 모든 행을 삭제한다
   * 
   * @param diaryNo
   * @throws DeleteException
   */
  public void deleteAll(int diaryNo) throws DeleteException;

}
