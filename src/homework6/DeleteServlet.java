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
import java.io.File;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteServlet() {
		super();
	}

	Connection c = null;

	public int deleteFolder(int id, List<MyFile> deleteFile, Integer owner_Id) throws ServletException {

		int parent=-1;
		String sql = "Delete from MyFile WHERE id=? and owner_id=?";
		try {
			java.sql.PreparedStatement stmnt = c.prepareStatement(sql);
			stmnt.setInt(1, id);
			stmnt.setInt(2, owner_Id);
			stmnt.executeUpdate();
			for (int i = 0; i < deleteFile.size(); i++) {
				MyFile f = deleteFile.get(i);
				if (f.getParent() == id) {
					if (f.folder) {
						deleteFolder(f.getId(), deleteFile, owner_Id);
					} else {

						File file = new File(getServletContext().getRealPath("/WEB-INF/files/" + f.getId()));
						stmnt.setInt(1, f.getId());
						stmnt.setInt(2, owner_Id);
						stmnt.executeUpdate();
						file.delete();
					}
				}
				if(f.getId()==id){
					System.out.println(f.id+" "+f.getParent());
					parent=f.getParent();
				}
			}

		} catch (SQLException e) {
			throw new ServletException(e);
		}
		return parent;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<MyFile> deleteFile = new ArrayList<>(); 

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");

		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";
			ResultSet rs;
			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			rs = stmt.executeQuery("Select * from MyFile where owner_id='" + owner_Id + "'");
			while (rs.next()) {
				deleteFile.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
						rs.getLong("size"), rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"),
						rs.getInt("owner_id")));
			}
			// request.setAttribute("NewUser", newUser);

			System.out.println("Delete Servlet Was Called");
			int id = Integer.parseInt(request.getParameter("id"));
			int parent=deleteFolder(id, deleteFile, owner_Id);


			if (parent == 0) {
				response.sendRedirect("Parentfolder");
				return;
			} else {
				response.sendRedirect("SubFolder?id=" + parent);
				return;
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
