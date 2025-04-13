import javax.servlet.*;
import java.sql.*;
import java.io.*;
public class RegistrationServlet extends GenericServlet{
	private Connection connection;
	private PreparedStatement preparedStatement;
    public void init(ServletConfig config) {
	  try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/USERS", "root", "Rakesh2004");
            preparedStatement = connection.prepareStatement("INSERT INTO USERS VALUES(?,?,?)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Log error
        } catch (SQLException e) {
            e.printStackTrace(); // Log error
        }
      }
     
      public void service(ServletRequest request, ServletResponse response)throws IOException {

		  if (preparedStatement == null) {
            response.getWriter().println("Database connection failed.");
            return; // Prevent further execution
        }

      String username=request.getParameter("user");
      String password=request.getParameter("pwd");
      String emailid=request.getParameter("email");

	  try{

          preparedStatement.setString(1, username);
          preparedStatement.setString(2, password);
          preparedStatement.setString(3, emailid);
          preparedStatement.executeUpdate();
	     }
	  catch(SQLException e)
		  {
		   e.printStackTrace(); // Log error
	      }

     response.setContentType("text.html");
     PrintWriter pw=response.getWriter();
     pw.println("<HTML>");
     pw.println("<BODY BGCOLOR= yellow>");
     pw.println("<H2>Registration successfully </H2>");
     pw.println("</BODY>");
     pw.println("</HTML>");
	 pw.close();
      }//servie
      public void destroy(){
		try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log error
        }

	  }
}