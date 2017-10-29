package homework6;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddFolderUsingAjax")
public class AddFolderUsingAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddFolderUsingAjax() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");

		Integer id = Integer.valueOf(request.getParameter("id"));

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		List<MyFile> file = new ArrayList<>();
		List<NewUser> userList = new ArrayList<>();
		List<MyFile> afterValue = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh.mm a");

		Integer afterId = null;
		String afterName = null;
		Date afterDate = null;
		long afterSize = 0;
		String date1 = null;
		;


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

				} else {

					for (int h = 0; h < file.size(); ++h) {
						if (file.get(h).getId() == id) {
							stmt.executeUpdate("insert into MyFile (name, type,size,parent_id,owner_id) values('" + name
									+ "', null ,0," + file.get(h).getId() + "," + owner_Id + " );");
							id = file.get(h).getId();

							break;
						}
					}
				}

				rs = stmt.executeQuery("Select * from MyFile where name='" + name + "' ");

				while (rs.next()) {
					afterValue.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
							rs.getLong("size"), rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"),
							rs.getInt("owner_id")));

				}
				for (int i = 0; i < afterValue.size(); i++) {
					System.out.println("id " + afterValue.get(i).getId());
					System.out.println("id " + afterValue.get(i).getName());
					System.out.println("id " + afterValue.get(i).getOwner_id());

					afterId = afterValue.get(i).getId();
					afterName = afterValue.get(i).getName();
					afterDate = afterValue.get(i).getDate();
					afterSize = afterValue.get(i).getSize();
					date1 = df.format(afterDate);
					System.out.println("Date " + afterDate + " Size " + afterSize);

				}

				request.setAttribute("id", afterId);
		
				String jsonString = "{\"afterId\": \"" + afterId + "\",\"afterName\":\"" + afterName + "\",\"date1\":\""
						+ date1 + "\"}";
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonString);

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
