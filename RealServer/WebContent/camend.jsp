<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="org.sqlite.*" %>
<%
	
	request.setCharacterEncoding("UTF-8");
	
	String ID = request.getParameter("ID");
	String PID = request.getParameter("PID");
	String Runtime = request.getParameter("Runtime");
	
	System.out.println(ID);
	System.out.println(PID);
	System.out.println(Runtime);

	Connection c = null;
	Statement stmt = null;
	try {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:enlisted.db");
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sql = "UPDATE ENLISTED SET CT ='" + Runtime + "' WHERE ID = '"+ ID + "';";
		stmt.executeUpdate(sql);
		c.commit();
		stmt.close();
		c.close();
	} catch ( Exception e ) {
		System.exit(0);
	}
%>