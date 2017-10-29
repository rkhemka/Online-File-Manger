package homework6;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Signup() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	
		HttpSession session = request.getSession();

		List<NewUser> newUser = new ArrayList<>();
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";
			ResultSet rs;
			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			rs = stmt.executeQuery("Select * from NewUser");

			while (rs.next())
				newUser.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));
			
			for (int i = 0; i < newUser.size(); i++) {
				List<MyFile> fac = new ArrayList<>();
				rs = stmt.executeQuery("Select * from MyFile where owner_id='" + newUser.get(i).getId() + "'");
				while (rs.next())
					fac.add(new MyFile(rs.getInt("id"), rs.getString("name"),rs.getString("type"), rs.getLong("size"),rs.getDate("date"),
							rs.getInt("parent_id"),rs.getBoolean("folder"),rs.getInt("owner_id")));
				newUser.get(i).setFiles(fac);
			}
			session.setAttribute("userList", newUser);

			c.close();
		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}

		request.setAttribute("error", request.getParameter("error"));
		request.getRequestDispatcher("/WEB-INF/Signup.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();

		String Newuser = request.getParameter("Newuser").trim();
		String Newpassword = request.getParameter("Newpassword").trim();

		List<NewUser> userList = (List<NewUser>) request.getSession().getAttribute("userList");
		Connection c = null;
	    try
	    {
	    	String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
	        String username = "cs3220stu25";
	        String password = "TM.EDnlV";
	       
	        c = DriverManager.getConnection( url, username, password );
	        Statement stmt = c.createStatement();
	        for(NewUser nu:userList)
				if(nu.getUserName().equals(Newuser) || Newpassword==null || Newpassword.isEmpty()|| Newuser==null || Newuser.isEmpty()){
					response.sendRedirect("Signup?error=User%20already%20exists/Password%20is%20empty");
					return;
				}
			
	        stmt.executeUpdate( "insert into NewUser (userName,password) values('"+Newuser+"', '"+Newpassword+"' );");
	        session.setAttribute("userList", userList );
			response.sendRedirect("Login");
	        
	        c.close();
	    }
	    catch( SQLException e )
	    {
	    	
	        throw new ServletException( e );
	    }
	    finally
	    {
	        try
	        {
	            if( c != null ) c.close();
	        }
	        catch( SQLException e )
	        {
	            throw new ServletException( e );
	        }
	    }	
	}
}
