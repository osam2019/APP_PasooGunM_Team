<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="org.sqlite.*" %>
<%@ page import="java.util.*"%>
<%

	request.setCharacterEncoding("UTF-8");

	String ID = request.getParameter("ID");

	JSONObject obj = new JSONObject();
	
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:officer.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "DELETE from OFFICER where ID='"+ID+"';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		obj.put("ACK", true);
%>
<%= obj.toJSONString() %>