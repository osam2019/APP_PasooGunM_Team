<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.json.simple.parser.*" %>
<%@ page import="org.sqlite.*" %>
<%@ page import="java.util.*"%>
<%
	
	request.setCharacterEncoding("UTF-8");
	
	String PID = request.getParameter("PID");
	
	JSONObject obj = new JSONObject();

		Connection c = null;
		Statement stmt = null;
		ArrayList<String> ids = new ArrayList<>();
		ArrayList<String> cts = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		int count = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:enlisted.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM ENLISTED;" );
			while ( rs.next() ) {
				String inid = rs.getString("ID");
				String inpid = rs.getString("PID");
				String inname = rs.getString("Name");
				String inct = rs.getString("CT");
				if(inpid.equals(PID)){
					count += 1;
					obj.put("ID"+count, inid);
					obj.put("CT"+count, inct);
					obj.put("Name"+count, inname);
					//cts.add(inct);
					//names.add(inname);
				}
			}
			rs.close();
			stmt.close();
			c.close();
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		obj.put("num", count);
%>
<%= obj.toJSONString() %>