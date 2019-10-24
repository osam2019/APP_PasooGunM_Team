<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%

request.setCharacterEncoding("UTF-8");

String ID = request.getParameter("ID");
String PW = request.getParameter("PW");
String Name = request.getParameter("Name");


JSONObject obj = new JSONObject();

Connection c = null;
Statement stmt = null;
try {
Class.forName("org.sqlite.JDBC");
c = DriverManager.getConnection("jdbc:sqlite:officer.db");
stmt = c.createStatement();
String sql = "CREATE TABLE IF NOT EXISTS OFFICER" +
			"(ID TEXT PRIMARY KEY     NOT NULL," +
			"PW            TEXT    NOT NULL," +
			"Name           TEXT    NOT NULL)"; 
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
		c1 = DriverManager.getConnection("jdbc:sqlite:officer.db");
		c1.setAutoCommit(false);
		stmt1 = c1.createStatement();
		String sql = "INSERT INTO OFFICER (ID,PW, Name) " +	
				"VALUES ('"+ID+"', '"+PW + "', '" + Name + "');"; 
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