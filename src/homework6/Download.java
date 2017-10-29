package homework6;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Download() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer owner_Id = (Integer) request.getSession().getAttribute("owner_Id");
		Integer DownloadId = Integer.parseInt(request.getParameter("id"));
		List<MyFile> fileDownload = new ArrayList<>();

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu25";
			String username = "cs3220stu25";
			String password = "TM.EDnlV";
			ResultSet rs;
			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();

			rs = stmt.executeQuery("Select * from MyFile where owner_id=" + owner_Id + "");

			while (rs.next()) {
				fileDownload.add(new MyFile(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
						rs.getLong("size"), rs.getDate("date"), rs.getInt("parent_id"), rs.getBoolean("folder"),
						rs.getInt("owner_id")));

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

		String path = getServletContext().getRealPath("/WEB-INF/files/" + DownloadId);
		File file = new File(path);
		
		String name = "";
		for (int i = 0; i < fileDownload.size(); i++){
			if(fileDownload.get(i).getId() == DownloadId){
		name = fileDownload.get(i).getName();

		response.setHeader("Content-Length", "" + file.length());
		response.setHeader("Content-Disposition", "attachment; filename=" + name);

		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		byte buffer[] = new byte[2048];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1)
			out.write(buffer, 0, bytesRead);
		in.close();
		}
		}

	}

}
