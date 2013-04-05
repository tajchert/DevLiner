import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Project implements Serializable{
	
	public int lineNumber;			//Current number of lines
	public String name;			//Project name ->title of folder
	public String description;		//Later on added description 
	private String notes;			//Private Notes
	private int lines = 0;			//Temporary value for counting methods (if number of lines has changed)
	private Format  dateFormatPrecise = new SimpleDateFormat("dd/MM/YYYY");
	
	ArrayList<DateAndLines> datesAndLines = new ArrayList<DateAndLines>();		//Store all value of lines - save by each check
	HashMap<String, Integer> daysAndLines = new HashMap<String, Integer>();		//Store only day and biggest value in that day of lines
	
	
	
	
	public Project(String name){
		this.name = name;
		lines = 0;
		setLineNumber(walk(Count.dirStart+"//"+name));
	}
	
	/*private void updateMaxDailyHistory(){
		for(DateAndLines item: datesAndLines){
			if(daysAndLines.size()==0){
				daysAndLines.put(item.getDate(), item.lineNumber);
			}
			if(daysAndLines==null){
				daysAndLines.put(item.getDate(), item.lineNumber);
			}
			if(item.lineNumber!=(int)daysAndLines.get(item.getDate())){
				System.out.println((int)daysAndLines.get(item.getDate()));
				daysAndLines.remove(item.getDate());
				daysAndLines.put(item.getDate(), item.lineNumber);
				System.out.println("AA");
				System.out.println("!"+daysAndLines);
				//System.out.println("retrieved element: " + daysAndLines);
			}
			
			}
	}*/
	public void showHistoryDays(){
		System.out.println("!"+daysAndLines);
	}
	
	public void updateLineNumber(){
		lines = 0;
		int tmp = lineNumber;
		setLineNumber(walk(Count.dirStart+"//"+name));
		if(tmp!= lineNumber || daysAndLines.size()==0){
			System.out.println("Change.");
			Date date = new Date();
			String dateDay = dateFormatPrecise.format(date);
			if(daysAndLines==null || daysAndLines.size()==0){
				System.out.println("Making list");
				daysAndLines.put(dateDay, lineNumber);
			}
			if(daysAndLines.get(dateDay)!=lineNumber){
				System.out.println("Making list");
				daysAndLines.put(dateDay, lineNumber);
			}
		}
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
		datesAndLines.add(new DateAndLines(date, lineNumber));
		//history.put(date, lineNumber);
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	//GETTERS
	public String getProjectHistory(){
		return datesAndLines+"";
	}
	public int getLines(){
		int res = this.lineNumber;
		return res;
	}
	public int getLinesAtDate(Date date){//TODO
		int res = 0;
		//int res = history.get(date);
		return res;
	}

	public String getNotes() {
		return notes;
	}
}
