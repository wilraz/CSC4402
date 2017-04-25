
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Servlet implementation class mainServlet
 */
public class mainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Set the response message's MIME type
	      response.setContentType("text/html; charset=UTF-8");
	      // Allocate a output writer to write the response message into the network socket
	      PrintWriter out = response.getWriter();
	      
	      String sid = request.getParameter("sId");
	      String sId = String.valueOf(sid);
	      
	   // JDBC driver name and database URL
			String JDBC_DRIVER = "com.mysql.jdbc.Driver";

			String DB_URL = "jdbc:mysql://localhost:3306/testdb";
			// Database credentials
		
			String USER = "root";
			String PASS = "HARnir88";
			Connection conn = null;
			
  	  out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Main Servlet</title></head>");
        out.println("<body>");
	      
	      try{
	          Class.forName(JDBC_DRIVER);
				//STEP 3: Open a connection
				System.out.println("Connecting to database...");
				
				conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
				//STEP 4: Execute a query
				System.out.println("Creating statement...");
				PreparedStatement ps =(PreparedStatement) conn.prepareStatement("SELECT * FROM members WHERE sid=?"); 
				//STEP 5: Extract data from result set
				ps.setString(1,sId);
				
				out.println("<table width=50% border=1>");  
				ResultSet rs = (ResultSet) ps.executeQuery();
			
				ResultSetMetaData rsmd=(ResultSetMetaData) rs.getMetaData();  
				int total=rsmd.getColumnCount();  
				out.println("<tr>");  
				for(int i=1;i<=total;i++)  
				{  
				out.println("<th>"+rsmd.getColumnName(i)+"</th>");  
				}   
				out.println("</tr>");  
	
				while(rs.next()){
					out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td></tr>");  
				}
				out.println("</table>");
				 
			 // Hyperlink "Back to the input page"
				out.println("<a href='AdminPage.html'> BACK </a>");
		          out.println("</body></html");
	      }catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{out.close();}
	      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
