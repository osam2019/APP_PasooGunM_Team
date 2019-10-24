<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="org.sqlite.*" %>
<%

	request.setCharacterEncoding("UTF-8");

	String ID = request.getParameter("ID");
	String PW = request.getParameter("PW");
	
	JSONObject obj = new JSONObject();

	boolean ispresent = false;
	Connection c = null;
	Statement stmt = null;
	try {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:officer.db");
		c.setAutoCommit(false);
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery( "SELECT * FROM OFFICER;" );
		while ( rs.next() ) {
			String inid = rs.getString("id");
			String inpw = rs.getString("pw");
			String inname = rs.getString("Name");
			if((ID.equals(inid)) && (PW.equals(inpw))){
				ispresent = true;
				obj.put("Name", inname);
				break;
			}
		}
		rs.close();
		stmt.close();
		c.close();
		if(ispresent == true) {
			obj.put("ACK", true);
			obj.put("PASS", true);
			System.out.println("t");
		}else {
			obj.put("ACK", true);
			obj.put("PASS", false);
			System.out.println("f");
		}
	}catch ( Exception e ) {
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);
	}

%>
<%= obj.toJSONString() %>