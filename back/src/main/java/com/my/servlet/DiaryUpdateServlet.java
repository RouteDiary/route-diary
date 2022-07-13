package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Client;
import com.my.dto.Diary;
import com.my.dto.Route;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;
import com.my.repository.RouteOracleRepository;
import com.my.repository.RouteRepository;

@WebServlet("/diaryupdate")
public class DiaryUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    String sample =
        "{\"diaryNo\" : 104, \"diaryTitle\" : \"titleupdate1\" , \"diaryStartDate\" : \"2021-07-01\",\"diaryEndDate\": \"2021-07-03\" , \"diaryDisclosureFlag\" : 1,\n"
            + "\"routes\" : [ {\"routeContent\" : \"코스타오리점\" , \"kakaoMapId\" : \"333\"},{\"routeContent\" : \"오리역화장실\" , \"kakaoMapId\" : \"10\"},{\"routeContent\" : \"강민주\" , \"kakaoMapId\" : \"19\"}]}";
    Diary diary = mapper.readValue(sample, Diary.class);
    List<Route> routes = diary.getRoutes();

    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");
    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    RouteRepository routeRepository = new RouteOracleRepository(envPath);

    try {
      Client client = new Client();
      client.setClientId("a11");
      diary.setClient(client);
      int diaryNo = diary.getDiaryNo();
      int routesRowSize = routes.size();
      for (int i = 0; i < routesRowSize; i++) {
        routes.get(i).setDiaryNo(diaryNo);
      }
      int originalRouteRows = routeRepository.selectRoutesRowSizeBydiaryNo(diaryNo);
      diaryRepository.update(diary);
      routeRepository.delete(diaryNo, originalRouteRows);
      routeRepository.insert(routes);

      map.put("status", 1);
      map.put("message", "DiaryOracleRepository.update() 성공");

    } catch (SelectException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (UpdateException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (DeleteException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (InsertException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }

}
