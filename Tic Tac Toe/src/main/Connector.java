package main;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Connector {

	public static Connection ConnectTo() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/tictactoe";
			String name = "root";
			String pass="Slaptas111!";
			
			Connection con = DriverManager.getConnection(url, name, pass);
			System.out.println("connected to database");
			return con;
			//con.close();
			
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			return null;
		}
	}	
}
