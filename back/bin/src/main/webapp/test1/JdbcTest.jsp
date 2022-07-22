<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.my.sql.MyConnection" %>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String envPath = application.getRealPath("project.properties");
Connection con = MyConnection.getConnection(envPath);
	String result=envPath;
	if(con==null) result="error db";
%>
<h1><%=result %></h1>
</body>
</html>

