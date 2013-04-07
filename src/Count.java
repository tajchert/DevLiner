import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Count {

	final static String dirStart ="D://Dropbox//workspace";		//Starting directory of projects ex. //workspace
	
	static ArrayList<Project> listOfProjects = new ArrayList<Project>();		//List of all projects
	static String listOfIgnoredProjects=".metadata GestureBuilder";		//Folders added here would be ignored when making new projects
	static String listOfIgnoredFiles=".git js .settings bin";		//Files and folders added here would be ignored
	
	boolean deleteOld = true;			//if to delete all old values
	boolean countAllLines = true;		//if false count only lines with ';' at the end
	
	
	public static void main(String[] args) {
		Count tmpp = new Count();
		File f = new File(dirStart);
		tmpp.readFile();
		tmpp.makeProjects(f);
		tmpp.updateProjects(f);
		//tmpp.getFileCount(f, 0);
		//System.out.println(tmpp.showHistory("A"));
		//System.out.println(tmpp.findProject("BazaPar").getProjectHistory());
		//tmpp.findProject("A").updateMaxDailyHistory();
		//System.out.println(tmpp.findProject("A").getProjectHistory());
		ExporterToJS expJS = new ExporterToJS(listOfProjects);
		tmpp.writeFile();
		expJS.createFile();
	}
	
	public String showHistory(String name){
		String res = "History, of: "+name +": ";
		Project tmp = findProject(name);
		//res += tmp.getHistory();
		return res;
	}
	
	public void makeProjects(File f){
		System.out.println(listOfProjects);
		File[] listOfFiles = f.listFiles();
		for (File file : listOfFiles){
            if (file.isDirectory() && !listOfIgnoredProjects.contains(file.getName())){
            	if(!isProjectOnList(file.getName())){
            		Project tmp = new Project(file.getName());
                	listOfProjects.add(tmp);
                    System.out.println("New project -> "+ tmp);
            	}
            }
        }
	}
	private void updateProjects(File f){
		File[] listOfFiles = f.listFiles();
		for (File file : listOfFiles){
            if (file.isDirectory()){
            	if(isProjectOnList(file.getName())){
            		findProject(file.getName()).updateLineNumber();
            		 System.out.println("Updated -> "+ file.getName());
            	}
            }
        }
	}
	private Project findProject(String name){
		Project res = null;
		for(Project searched : listOfProjects){
	        if(searched.name.equals(name)){
	        	res = searched;
	        }
	    }
		return res;
	}
	private boolean isProjectOnList(String name){
		if(listOfProjects.isEmpty()){
			return false;
		}
		for (Project tmpSearched : listOfProjects) {
			   if ((tmpSearched.name).equals(name)) {
			       return true;
			   }
			}
		return false;
	}
	

	private boolean deleteProjects(){
		String fileName = "ProjectList.obj";
	    File f = new File(fileName);
		if (!f.exists()) {
			throw new IllegalArgumentException("Delete not performed: no such file or directory: " + fileName);
		}
		if (!f.canWrite()) {
			throw new IllegalArgumentException("Delete not performed: write protected: "+ fileName);
		}
	    boolean success = f.delete();
	    return success;
	}
	private void writeFile(){
		try {
			FileOutputStream saveList = new FileOutputStream("ProjectList.obj");
			ObjectOutputStream save = new ObjectOutputStream(saveList);
			save.writeObject(listOfProjects);
			save.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}

	private void readFile(){
		try {
			FileInputStream saveFile = new FileInputStream("ProjectList.obj");
			ObjectInputStream save = new ObjectInputStream(saveFile);
			listOfProjects = (ArrayList<Project>) save.readObject();
			save.close();
		} catch (FileNotFoundException e) {
			//Ignore just make new one
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}

}
