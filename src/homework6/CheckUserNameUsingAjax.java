package homework6;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckUserNameUsingAjax
 */
@WebServlet("/CheckUserNameUsingAjax")
public class CheckUserNameUsingAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckUserNameUsingAjax() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String message = null;
		HttpSession session = request.getSession();

		String Newuser = request.getParameter("name").trim();
		String Newpassword = request.getParameter("pass").trim();

		List<NewUser> userList = (List<NewUser>) request.getSession().getAttribute("userList");
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			for (NewUser nu : userList)
				if (nu.getUserName().equals(Newuser) || Newpassword == null || Newpassword.isEmpty() || Newuser == null
						|| Newuser.isEmpty()) {
					message = "Username Already Exists / Username Does Not Exists";
					break;

				} else {
					stmt.executeUpdate("insert into NewUser (userName,password) values('" + Newuser + "', '"
							+ Newpassword + "' );");
					message = "true";

				}

			session.setAttribute("userList", userList);

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

		response.setContentType("text/plain");
		response.getWriter().print(message);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
