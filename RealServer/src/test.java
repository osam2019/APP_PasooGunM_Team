import java.util.*;
import java.sql.*;

public class test {
	
	public static void main(String[] args){
	String ID = "leader!";
	Connection c = null;
	Statement stmt = null;
	ArrayList<String> ids = new ArrayList<>();
	ArrayList<String> cts = new ArrayList<>();
	ArrayList<String> names = new ArrayList<>();
	ArrayList<String> send = new ArrayList<>();
	ArrayList<String> test = new ArrayList<>();
	int count = 0;
	try {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:enlisted.db");
		c.setAutoCommit(false);
		System.out.println("Opened database successfully");
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery( "SELECT * FROM ENLISTED;" );
		while ( rs.next() ) {
			String inid = rs.getString("ID");
			String inpid = rs.getString("PID");
			String inname = rs.getString("Name");
			String inct = rs.getString("CT");
			if(inpid.equals(ID)){
				ids.add(inid);
				cts.add(inct);
				names.add(inname);
				count += 1;
			}
		}
		rs.close();
		stmt.close();
		c.close();
	}catch ( Exception e ) {
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);
	}
	Object[] array = ids.toArray();
	for(int i =0; i<count; i++){
		test.add("IDs"+i);
		test.add(array[i]);
		//System.out.println(ids[i]);
		send.addAll(test);
		test.clear();
	//obj.put("CTs", cts);
	//obj.put("Names", names);
	}
	}
}
