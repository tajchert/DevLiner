import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Project implements Serializable{
	
	public int lineNumber;			//Current number of lines
	public String name;			//Project name ->title of folder
	public String description;		//Later on added description 
	private String notes;			//Private Notes
	private int lines = 0; 				
	public Map<Date,Integer> history = new HashMap<Date,Integer>();
	
	
	public Project(String name){
		this.name = name;
		lines = 0;
		setLineNumber(walk(Count.dirStart+"//"+name));
	}
	public void updateLineNumber(){
		lines = 0;
		setLineNumber(walk(Count.dirStart+"//"+name));
	}
	
	//Important methods
	private int walk(String path) {			//Before walk please make 'lines = 0';
		File prjct = new File(path);
		File[] list = prjct.listFiles();
		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath());
				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				System.out.println("File:" + f.getAbsoluteFile());
				try {
					lines+=count(f.getAbsoluteFile());
				} catch (IOException e) {
					//Most likely file not found
				}
			}
		}
		return lines;
	}
	
	private int count(File file) throws IOException {
		FileInputStream is = new FileInputStream(file);
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == ';') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
	
	
	
	//Some not interesting crap
	@Override
	public String toString() {
		return "Project [" + name + ", " + lineNumber+ "]";
	}
	
	
	//Even less interesting crap
	
	//SETTERS
	public void setLineNumber(int lineNumber) {
		Date date = new Date();
		this.lineNumber = lineNumber;
		history.put(date, lineNumber);
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	//GETTERS
	public int getLines(){
		int res = this.lineNumber;
		return res;
	}
	public int getLinesAtDate(Date date){
		int res = history.get(date);
		return res;
	}

	public String getNotes() {
		return notes;
	}

	
}
