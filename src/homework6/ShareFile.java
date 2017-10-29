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

/**
 * Servlet implementation class ShareFile
 */
@WebServlet("/ShareFile")
public class ShareFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShareFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println("ShareFile");

		String SharedUser = request.getParameter("fileSharedWithUser");

		Integer fileid = Integer.valueOf(request.getParameter("fileid"));

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");

		String message = null;

		boolean share = true;

		Integer SharedUserId = -1;

		System.out.println("nknjk0   " + SharedUser + "dscsv      " + fileid + " nsm acn,   " + owner_Id + "");

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

			rs = stmt.executeQuery("Select * from MyFile where owner_id='" + owner_Id + "' and id ='" + fileid + "'");

			while (rs.next()) {
				file.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"), rs.getInt("owner_id")));

			}

			rs = stmt.executeQuery("Select * from NewUser where owner_id!='" + owner_Id + "' ");
			while (rs.next())
				userList.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));

			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getUserName().equals(SharedUser)) {
					SharedUserId = userList.get(i).getId();
					share = true;
					message = "File Shared Sucessfully";
					System.out.println("I changed the booleean value while checking username \n");
					break;
				} else {
					share = false;
					System.out.println("I changed the booleean value while username did not match \n\n");
					message = "File Not Shared Sucessfully";

					// break;
				}
			}

			for (int i = 0; i < file.size(); i++) {
				System.out.println("Before If value is =" + file.get(i).getId());
				if (fileid == file.get(i).getId() && share) {
					System.out.println("I AM inside");
					stmt.executeUpdate("insert into MyFile (name, type, size, date,parent_id,folder, owner_id) values('"
							+ file.get(i).getName() + "','" + file.get(i).getType() + "','" + file.get(i).getSize()
							+ "','" + file.get(i).getDate() + "','" + file.get(i).getParent() + "','" + 0 + "','"
							+ SharedUserId + "');");

					share = true;
					System.out.println("I changed the booleean value while checking file id  ");

					break;
				} else {
					share = false;
					System.out.println("I changed the booleean value while  file id did not matched  ");

				}
			}
			System.out.println("owner of share file" + SharedUserId);
			System.out.println("id of share file" + fileid);
			System.out.println("value of share file" + share);

			request.setAttribute("share", share);

			stmt.executeUpdate(
					"insert into ShareFile (owner_id,file_id) values( '" + SharedUserId + "','" + fileid + "');");
			request.setAttribute("SharedUserId", SharedUser);

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

	     response.setContentType( "text/plain" );
	        response.getWriter().print( message );
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
