<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="org.sqlite.*" %>
<%
	
	request.setCharacterEncoding("UTF-8");
	
	String ID = request.getParameter("ID");
	String PW = request.getParameter("PW");
	System.out.println(ID);
	System.out.println(PW);
	
	JSONObject obj = new JSONObject();

		boolean ispresent = false;
		Connection c = null;
		Statement stmt = null;
		String pidref = "";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:enlisted.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM ENLISTED;" );
			while ( rs.next() ) {
				String inid = rs.getString("ID");
				String inpw = rs.getString("PW");
				String inpid = rs.getString("PID");
				if((ID.equals(inid)) && (PW.equals(inpw))){
					ispresent = true;
					pidref = inpid;
					System.out.println("exists");
					break;
				}
			}
			rs.close();
			stmt.close();
			c.close();
			if(ispresent == true) {
				obj.put("ACK", true);
				obj.put("PASS", true);
				obj.put("PID", pidref);
				System.out.println("t");
			}else {
				obj.put("ACK", true);
				obj.put("PASS", false);
				obj.put("PID", pidref);
				System.out.println("f");
				
			}
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println(pidref);
%>
<%= obj.toJSONString() %>