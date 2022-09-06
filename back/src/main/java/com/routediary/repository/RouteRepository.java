package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Route;


@Repository
@Mapper
public interface RouteRepository {

  /**
   * 각 다이어리에 속한 루트의 개수를 반환
   *
   * @param diaryNo
   * @return int
   */
  public int selectCount(int diaryNo);

  /**
   * Route(루트) 객체를 DB에 추가
   *
   * @param route
   */
  public void insert(Route route);

  /**
   * diaryNo, routeNo가 일치하는 루트를 삭제한다
   *
   * @param diaryNo
   * @param routeNo
   */
  public void delete(int diaryNo, int routeNo);

  /**
   * 해당 diaryNo의 모든 행을 삭제한다
   *
   * @param diaryNo
   */
  public void deleteAll(int diaryNo);

}
