package homework6;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rajat
 *
 */
public class MyFile {
	Integer id;
	String name;
	String type; // content type of the uploaded file
	long size;
	Date date;
	Integer parent;
	boolean folder;
	Integer owner_id;
	List<NewUser> userfile;

	public List<NewUser> getUserfile() {
		return userfile;
	}

	public void setUserfile(List<NewUser> userfile) {
		this.userfile = userfile;
	}

	MyFile(Integer id, String name, String type, long size, Date date, Integer parent, boolean folder, Integer owner_id) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.date = date;
		this.parent = parent;
		this.folder = folder;
		this.owner_id=owner_id;
		userfile= new ArrayList<NewUser>();
		
	}

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}



	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	
}
