package homework6;

public class SharedFiles {

	
	Integer owner_id;
	Integer file_id;
	
	
	public SharedFiles(Integer owner_id , Integer file_id) {
		// TODO Auto-generated constructor stub
		
		this.owner_id=owner_id;
		this.file_id=file_id;
		
	}


	public Integer getOwner_id() {
		return owner_id;
	}


	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}


	public Integer getFile_id() {
		return file_id;
	}


	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}
	
	
	
	
}
