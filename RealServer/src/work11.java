import java.sql.*;

public class work11 {
  public static void main( String args[] ) {
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
						" BK           INT    NOT NULL, " +
						" CT            INT     NOT NULL)"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.exit(0);
		}
		
   }
}
