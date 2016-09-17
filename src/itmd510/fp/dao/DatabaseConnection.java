package itmd510.fp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection
{
	public static Connection createConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3306/dbfp", "fpuser", "510");
		System.out.println("Connection Established.");
		return connection;
	}

	public static PreparedStatement prepareStatement(String statement) throws ClassNotFoundException, SQLException
	{
		PreparedStatement stmt = createConnection().prepareStatement(statement);
		return stmt;
	}
}
