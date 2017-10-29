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

@WebServlet("/SubFolder")
public class SubFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SubFolder() {
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
		List<MyFile> SubFile = new ArrayList<>();


		
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";
			ResultSet rs;
			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			rs = stmt.executeQuery("Select * from MyFile where owner_id=" + owner_Id + ";");

			while (rs.next()) {	
				SubFile.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"), rs.getInt("owner_id")));

			}
			
			
			request.setAttribute("MyFile", SubFile);
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
			
		Integer Sindex = Integer.parseInt(request.getParameter("id"));
		MyFile entry = getEntry(Sindex,request,SubFile);
	//	System.out.println("dcfgvhbjkkknjn                    hjjjjjjjjjj"+entry.getParent());
		request.setAttribute("index", Sindex);
		request.setAttribute("MyFile", SubFile);
		request.setAttribute("SelectedID", entry);

		
		
		request.getRequestDispatcher("/WEB-INF/SubFolder.jsp").forward(request, response);
		;

	}

}
