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

@WebServlet("/NewFolder")
public class NewFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static int idSeed = 100;

	public NewFolder() {
		super();
		// TODO Auto-generated constructor stub
	}

	private MyFile getEntry(Integer id, HttpServletRequest request, List<MyFile> file) {

		for (MyFile entry : file)
			if (entry.getId().equals(id))
				return entry;

		return null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer nindex = -1;

		if (request.getParameter("id").length() != 0)
			nindex = Integer.parseInt(request.getParameter("id"));

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		List<MyFile> file = new ArrayList<>();
		List<NewUser> userList = new ArrayList<>();

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery("Select * from MyFile where owner_id='" + owner_Id + "' ");

			while (rs.next()) {
				file.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"), rs.getInt("owner_id")));

			}

			rs = stmt.executeQuery("Select * from NewUser where owner_id='" + owner_Id + "' ");
			while (rs.next())
				userList.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));

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

		MyFile entry = getEntry(nindex, request, file);
		getServletContext().setAttribute("index", nindex);
		request.setAttribute("SelectedNewId", entry);

		request.getRequestDispatcher("/WEB-INF/NewFolder.jsp").forward(request, response);
		;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("FolderName");
		Integer id = Integer.valueOf(request.getParameter("id"));

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		List<MyFile> file = new ArrayList<>();
		List<NewUser> userList = new ArrayList<>();

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery("Select * from MyFile where owner_id='" + owner_Id + "' ");

			while (rs.next()) {
				file.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"), rs.getInt("owner_id")));

			}

			rs = stmt.executeQuery("Select * from NewUser where owner_id='" + owner_Id + "' ");
			while (rs.next())
				userList.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));

			{
				if (id == 0) {

					stmt.executeUpdate("insert into MyFile (name, type,size,parent_id,owner_id) values( '" + name
							+ "',null,0,0," + owner_Id + " );");

					response.sendRedirect("Parentfolder");
					return;
				} else {

					for (int h = 0; h < file.size(); ++h) {
						if (file.get(h).getId() == id) {
							stmt.executeUpdate("insert into MyFile (name, type,size,parent_id,owner_id) values('" + name
									+ "', null ,0," + file.get(h).getId() + "," + owner_Id + " );");

							break;
						}
					}

					response.sendRedirect("SubFolder?id=" + id);
					return;
				

			       
				}

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

	}

}
