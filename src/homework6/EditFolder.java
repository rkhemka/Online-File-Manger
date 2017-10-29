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

@WebServlet("/EditFolder")
public class EditFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditFolder() {
		super();

	}

	private MyFile getEntry(Integer id, HttpServletRequest request, List<MyFile> file) {
		
		for (MyFile entry : file)
			if (entry.getId().equals(id))
				return entry;

		return null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		List<MyFile> file = new ArrayList<>();
		List<NewUser> userList= new ArrayList<>();

		System.out.println("I am EDit Servlet Ajax c");
		
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet	rs ;

			
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
		
		


		Integer Eindex = Integer.parseInt(request.getParameter("id"));
		getServletContext().setAttribute("index", Eindex);
		request.setAttribute("MyFile",file);


		request.getRequestDispatcher("/WEB-INF/EditFolder.jsp").forward(request, response);
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		Integer id = Integer.valueOf(request.getParameter("id"));
		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		List<MyFile> EditFile = new ArrayList<>();
		List<NewUser> userList= new ArrayList<>();

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet	rs ;

			
		 	rs = stmt.executeQuery("Select * from MyFile where owner_id='" + owner_Id + "' ");
			

			while (rs.next()) {
				EditFile.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"), rs.getInt("owner_id")));
			

			}


		 	rs = stmt.executeQuery("Select * from NewUser where owner_id='" + owner_Id + "' ");
		 	while (rs.next())
				userList.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));

		 	
		MyFile entry = getEntry(id,request,EditFile);
		
		int parent = -1;
		for (int i = 0; i < EditFile.size(); i++) {
			if (EditFile.get(i).getId() == id) {

				if (EditFile.get(i).getParent() != 0 && EditFile.get(i).isFolder()) {
					
					parent = EditFile.get(i).getParent();
					
					System.out.println("I am inside IFFFF"+parent);

				} else if (EditFile.get(i).getParent() != 0 && !(EditFile.get(i).isFolder()) ) {

					parent = EditFile.get(i).getParent();

				}

			}

		}
		
		String value =request.getParameter("Name");

		String sql = "UPDATE MyFile SET name=? WHERE id=? and owner_id=?";
		 
		java.sql.PreparedStatement statement = c.prepareStatement(sql);
		
					statement.setString(1, value);
					statement.setInt(2, entry.getId());
					statement.setInt(3, owner_Id);
					statement.executeUpdate();
		
		
		if (parent == -1) {
			response.sendRedirect("Parentfolder");
			return;
		} else {
			response.sendRedirect("SubFolder?id=" + parent);
			return;
		}
		
	}
		
		catch (SQLException e) {

			throw new ServletException(e);
		}
		finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
	}

}
