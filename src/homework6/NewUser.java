package homework6;

import java.util.ArrayList;
import java.util.List;

public class NewUser {

	private String userName;
	private String passrword;
	private Integer id;
	
	List<MyFile> files;
	
	NewUser( String userName, String password, Integer id){
		this.userName=userName;
		this.passrword=password;
		this.id =id;
		files =new ArrayList<MyFile>();
	
		
	}

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}




	public List<MyFile> getFiles() {
		return files;
	}


	public void setFiles(List<MyFile> files) {
		this.files = files;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassrword() {
		return passrword;
	}

	public void setPassrword(String passrword) {
		this.passrword = passrword;
	}
	
	
}
