package com.routediary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Route;
import com.routediary.exception.DeleteException;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;

@SpringBootTest
class RouteRepositoryTest {

  @Autowired
  RouteRepository routeRepository;

   @Test
  void selectCountTest() throws SelectException {
    int diaryNo = 2;
    int count = routeRepository.selectCount(diaryNo);
    int expectedCount = 5;
    assertEquals(expectedCount, count);
  }

   @Test
  void insertTest() throws InsertException {
    int diaryNo = 3;
    String routeCotent = "ILikeParty";
    String kakaoMapId = "4";
    Route route = new Route(); // route 객체 생성
    route.setDiaryNo(diaryNo); // route 객체의 diaryNo 멤버변수의 값을 설정
    route.setRouteContent(routeCotent);
    route.setKakaoMapId(kakaoMapId);

    routeRepository.insert(route);
  }

   @Test
  void deleteTest() throws DeleteException {
    int diaryNo = 3;
    int routeNo = 3;

    routeRepository.delete(diaryNo, routeNo);
  }

  @Test
  void deleteAllTest() throws DeleteException {
    int diaryNo = 3;

    routeRepository.deleteAll(diaryNo);
  }
}
