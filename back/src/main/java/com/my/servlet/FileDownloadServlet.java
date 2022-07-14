package com.my.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/filedownload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2mb
    maxFileSize = 1024 * 1024 * 10, // 10mb
    maxRequestSize = 1024 * 1024 * 50 // 50mb
// location = "images" // 파일저장위치
)
public class FileDownloadServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    // 경로
    // final String path = request.getParameter("destination");
    String path = getServletContext().getInitParameter("filePath");
    String clientId = request.getParameter("client_id");
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    int routeNo = Integer.parseInt(request.getParameter("route_no"));
    String directoryName = path + "/" + diaryNo;
    File saveDirectory = new File(directoryName);
    if (!saveDirectory.exists()) {
      System.out.println("업로드 실제경로(" + directoryName + ")생성");
      saveDirectory.mkdirs();
    }
    System.out.println("saveDirectory.getAbsolutePath()=" + path);
    Collection<Part> parts = request.getParts();
    int i = 1;
    for (Part part : parts) {
      String paramName = part.getName();
      System.out.println("part.getName()=" + paramName + ", part.getSubmittedFileName()="
          + part.getSubmittedFileName() + ", part.getSize()=" + part.getSize());
      String fileName = routeNo + "_" + i;
      i++;
      part.write(saveDirectory.getAbsolutePath() + "/" + fileName + ".png");
    }

  }
}
