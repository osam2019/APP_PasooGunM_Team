<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%
	
	request.setCharacterEncoding("UTF-8");
	
	String ID = request.getParameter("ID");
	String PID = request.getParameter("PID");
	String PW = request.getParameter("PW");
	String Name = request.getParameter("Name");
	
	JSONObject obj = new JSONObject();
	
	Connection c = null;
	Statement stmt = null;
	try {
	Class.forName("org.sqlite.JDBC");
	c = DriverManager.getConnection("jdbc:sqlite:enlisted.db");
	stmt = c.createStatement();
	String sql = "CREATE TABLE IF NOT EXISTS ENLISTED" +
				"(ID TEXT PRIMARY KEY     NOT NULL," +
				" PID           TEXT    NOT NULL, " + 
				" PW           TEXT    NOT NULL, " +
				" Name           TEXT    NOT NULL, " +
				" CT            INT     NOT NULL)"; 
	System.out.println("created");
	stmt.executeUpdate(sql);
	stmt.close();
	c.close();
	} catch ( Exception e ) {
		System.exit(0);
	}
	
	Connection c1 = null;
	Statement stmt1 = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c1 = DriverManager.getConnection("jdbc:sqlite:enlisted.db");
			c1.setAutoCommit(false);
			stmt1 = c1.createStatement();
			String sql = "INSERT INTO ENLISTED (ID,PID,PW,Name,CT) " +	
						"VALUES ('"+ID+"', '"+PID+"', '"+ PW +"', '"+ Name +"', '"+ 0 + "');";
			stmt1.executeUpdate(sql);
			stmt1.close();
			c1.commit();
			c1.close();			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		obj.put("ACK", true);
%>
<%= obj.toJSONString() %>