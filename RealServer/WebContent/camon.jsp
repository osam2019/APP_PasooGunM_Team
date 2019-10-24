<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="org.sqlite.*" %>
<%
	
	request.setCharacterEncoding("UTF-8");
	
	String ID = request.getParameter("ID");
	String PID = request.getParameter("PID");

	JSONObject obj = new JSONObject();
	
	obj.put("ACK", true);
%>
<%= obj.toJSONString() %>