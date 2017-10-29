package homework6;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Upload() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer index = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("index", index);

		request.getRequestDispatcher("/WEB-INF/Upload.jsp").forward(request, response);
		;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer index = 0;
		HttpSession session =request.getSession();
		// List<MyFile> fileUpload = (List<MyFile>)
		// request.getSession().getAttribute("MyFile");

	//	int uniqueId = (int) getServletContext().getAttribute("uniqueId");

		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		ServletFileUpload upload = new ServletFileUpload(factory);

		String fileDir = getServletContext().getRealPath("/WEB-INF/files");
		String fileName = "";
		File file = null;
		try {
		
		

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		List<MyFile> fileUpload = new ArrayList<>();
		List<NewUser> userList = new ArrayList<>();

		Connection c = null;
		try {
			
			Integer maxId=0;
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet rs;
			
			rs= stmt.executeQuery("Select max(id) from MyFile;");
			while(rs.next()){
			maxId = rs.getInt("max(id)");
			}
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (!item.isFormField()) {
					fileName = (new File(item.getName())).getName();
					file = new File(fileDir,  (maxId+1)+ "");
					item.write(file);
					session.setAttribute("DownloadId", (maxId+1));
				}

				else {
					if (item.getFieldName().equals("id")) {
						index = Integer.parseInt(item.getString());
					}
				}
			}

			
		

			rs = stmt.executeQuery("Select * from MyFile where owner_id='" + owner_Id + "' ");

			while (rs.next()) {
				fileUpload.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
						rs.getLong("size"), rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"),
						rs.getInt("owner_id")));

			}

			rs = stmt.executeQuery("Select * from NewUser where owner_id='" + owner_Id + "' ");
			while (rs.next())
				userList.add(new NewUser(rs.getString("userName"), rs.getString("password"), rs.getInt("owner_Id")));

		
			if (index == 0) {

					stmt.executeUpdate("insert into MyFile (name, type,size,parent_id,folder,owner_id) values( '"
							+ fileName + "','" + (FilenameUtils.getExtension(fileDir + "" + file.getName())) + "',"
							+ file.length() + ",0,false," + owner_Id + " );");


				response.sendRedirect("Parentfolder");
				return;

			} else {

				for (int i = 0; i < fileUpload.size(); ++i) {

					if (fileUpload.get(i).getId() == index) {
					
						
						stmt.executeUpdate("insert into MyFile (name, type,size,parent_id,folder,owner_id) values( '"
								+ fileName + "','" + (FilenameUtils.getExtension(fileDir + "" + file.getName())) + "',"
								+ file.length() + ","+fileUpload.get(i).getId()+","+false+"," + owner_Id + " );");
						break;
					}
				}


			}
		} catch (SQLException e) {

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
		
		
		} catch (Exception e) {
			throw new IOException(e);
		}

		response.sendRedirect("SubFolder?id=" + index + "");
	}
}
