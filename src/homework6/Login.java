package homework6;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}

	//	getServletContext().setAttribute("uniqueId", 0);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("MyFile") == null) {
			request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
		} else {
			System.out.println(request.getSession().getAttribute("MyFile"));
			response.sendRedirect("Parentfolder");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String uname = request.getParameter("username");
		String pass = request.getParameter("password");
		request.getSession().setAttribute("user", uname);

		List<NewUser> userList = new ArrayList<>();

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("Select * from NewUser");

			while (rs.next())
				userList.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));

			for (int i = 0; i < userList.size(); i++) {
				List<MyFile> fac = new ArrayList<>();
				rs = stmt.executeQuery("Select * from MyFile where owner_id='" + userList.get(i).getId() + "'");
				while (rs.next())
					fac.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
							rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"),
							rs.getInt("owner_id")));
				userList.get(i).setFiles(fac);
			}


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

		for (int i = 0; i < userList.size(); i++) {
			if (uname.equals(userList.get(i).getUserName()) && pass.equals(userList.get(i).getPassrword())) {
				List<MyFile> file = userList.get(i).getFiles();
			
				session.setAttribute("owner_Id", userList.get(i).getId());
				session.setAttribute("MyFile", file);
				session.setAttribute("userList", userList);

				response.sendRedirect("Parentfolder");
				return;

			}
		}
		response.sendRedirect("Login");
	}

}