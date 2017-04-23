
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TutorPull
 */
public class load_stuff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public load_stuff() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final PrintWriter out = response.getWriter();
		
		String sid = request.getParameter("sid");
		//String name = request.getParameter("name");
		String fname = request.getParameter("fname");
		//String email = " ";
		String lname = request.getParameter("lname");

		//String phone = " ";
		//String subject = " ";
				
		// JDBC driver name and database URL
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		//String DB_URL = "jdbc:mysql://35.161.16.126:3306/webDB";
		//String DB_URL = "jdbc:mysql://localhost:3306/webDB";
		String DB_URL = "jdbc:mysql://localhost:3306/testdb";
		// Database credentials
		//String USER = "root";
		String USER = "root";
		String PASS = "HARnir88";
		Connection conn = null;
		Statement stmt = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName(JDBC_DRIVER);
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT * FROM sororoties";
			
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			rs.next();
			//Retrieve by column name
			
			sid = rs.getString("sid");
			//name = rs.getString("name");
			fname = rs.getString("fname");
			lname = rs.getString("lname");
			//phone = rs.getString("phone");
			//email = rs.getString("email");
			//subject = rs.getString("subject");
						
			} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType +
		"<html>\n" +
		//"<head><title>Tutor Retrieval</title></head>\n" +
		"<body>\n" +
		"<ul>\n" +
		//" <li><b>Tutor name</b>: " + name + "\n" +
		" <li><b>Student id</b>: " + sid + "\n" +
		" <li><b>Student  name</b>: " + fname + " " + lname + "\n" +
		//" <li><b>Tutor email</b>: " + email + "\n" +
		//" <li><b>Tutor phone</b>: " + phone + "\n" +
		//" <li><b>Tutor subject</b>: " + subject + "\n" + 
		"</ul>\n" +
		//"<a href='http://www.geauxtutors.com/ji02Td5aR1v8xAS5Ph590lgds21rc45aq1SeRf43CFSQ895lh.html'>" +
		//"Return to GeauxTutors Admin page</a>" +
		"<a href= 'http://localhost:8080/result.html'>" + "reurn to the admin page</a>" +
		"</ul>\n" +
		"</body></html>");
	}
}


