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
 * Servlet implementation class UserFiles
 */
@WebServlet("/UserFiles")
public class UserFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");

		
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";
			ResultSet rs;

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();

			
			rs = stmt.executeQuery("Select * from MyFile where owner_id=" + owner_Id + ";");
			List<MyFile> fac1 = new ArrayList<>();
			
			List<SharedFiles> sharedFiles = new ArrayList<>();

			while (rs.next()) {
				fac1.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"), rs.getInt("owner_id")));

			}
			
			rs= stmt.executeQuery("Select * from ShareFile where owner_id=" + owner_Id + ";");
			while (rs.next()) {
				sharedFiles.add(new SharedFiles(rs.getInt("owner_id"),rs.getInt("file_id")));
			}
			
			List<String> sharedUserFiles = new ArrayList<>();
			
			if(sharedFiles.size()==0){
				
			}
			else{
				for(int i=0; i< sharedFiles.size();i++){
				
					rs = stmt.executeQuery("Select name from MyFile where id=" + sharedFiles.get(i).getFile_id()+ ";");
					while(rs.next()){
						sharedUserFiles.add(rs.getString("name"));
					}
					
				}
			}
		
	/*
	 select * from MyFile f
right join ShareFile s
on  f.owner_id=s.owner_id and f.folder=0;
	 */
	
			
			System.out.println("size value   "+sharedFiles.size());
			
			request.setAttribute("MyFile", fac1);
			request.setAttribute("length", sharedFiles.size());
			request.setAttribute("sharedUserFiles", sharedUserFiles);
			
		//	request.setAttribute("share", share);


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

		request.getRequestDispatcher("/WEB-INF/UserFiles.jsp").forward(request, response);
		;

	}
}
